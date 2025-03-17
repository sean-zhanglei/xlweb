package com.nbug.module.infra.service.logger;

import cn.hutool.core.util.StrUtil;
import com.nbug.depends.tenant.core.context.TenantContextHolder;
import com.nbug.depends.tenant.core.util.TenantUtils;
import com.nbug.mico.common.model.logger.ApiErrorLog;
import com.nbug.mico.common.pojo.PageResult;
import com.nbug.mico.common.utils.object.BeanUtils;
import com.nbug.module.infra.api.logger.dto.ApiErrorLogCreateReqDTO;
import com.nbug.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import com.nbug.module.infra.dal.ApiErrorLogDao;
import com.nbug.module.infra.enums.logger.ApiErrorLogProcessStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static com.nbug.mico.common.exception.utils.ServiceExceptionUtil.exception;
import static com.nbug.mico.common.model.logger.ApiErrorLog.REQUEST_PARAMS_MAX_LENGTH;
import static com.nbug.module.infra.enums.ErrorCodeConstants.API_ERROR_LOG_NOT_FOUND;
import static com.nbug.module.infra.enums.ErrorCodeConstants.API_ERROR_LOG_PROCESSED;

/**
 * API 错误日志 Service 实现类
 *
 * @author NUBG
 */
@Service
@Validated
@Slf4j
public class ApiErrorLogServiceImpl implements ApiErrorLogService {

    @Resource
    private ApiErrorLogDao apiErrorLogDao;

    @Override
    public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        ApiErrorLog apiErrorLog = BeanUtils.toBean(createDTO, ApiErrorLog.class)
                .setProcessStatus(ApiErrorLogProcessStatusEnum.INIT.getStatus());
        apiErrorLog.setRequestParams(StrUtil.maxLength(apiErrorLog.getRequestParams(), REQUEST_PARAMS_MAX_LENGTH));
        if (TenantContextHolder.getTenantId() != null) {
            apiErrorLogDao.insert(apiErrorLog);
        } else {
            // 极端情况下，上下文中没有租户时，此时忽略租户上下文，避免插入失败！
            TenantUtils.executeIgnore(() -> apiErrorLogDao.insert(apiErrorLog));
        }
    }

    @Override
    public PageResult<ApiErrorLog> getApiErrorLogPage(ApiErrorLogPageReqVO pageReqVO) {
        return apiErrorLogDao.selectPage(pageReqVO);
    }

    @Override
    public void updateApiErrorLogProcess(Long id, Integer processStatus, Long processUserId) {
        ApiErrorLog errorLog = apiErrorLogDao.selectById(id);
        if (errorLog == null) {
            throw exception(API_ERROR_LOG_NOT_FOUND);
        }
        if (!ApiErrorLogProcessStatusEnum.INIT.getStatus().equals(errorLog.getProcessStatus())) {
            throw exception(API_ERROR_LOG_PROCESSED);
        }
        // 标记处理
        apiErrorLogDao.updateById(ApiErrorLog.builder().id(id).processStatus(processStatus)
                .processUserId(processUserId).processTime(LocalDateTime.now()).build());
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public Integer cleanErrorLog(Integer exceedDay, Integer deleteLimit) {
        int count = 0;
        LocalDateTime expireDate = LocalDateTime.now().minusDays(exceedDay);
        // 循环删除，直到没有满足条件的数据
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            int deleteCount = apiErrorLogDao.deleteByCreateTimeLt(expireDate, deleteLimit);
            count += deleteCount;
            // 达到删除预期条数，说明到底了
            if (deleteCount < deleteLimit) {
                break;
            }
        }
        return count;
    }

}
