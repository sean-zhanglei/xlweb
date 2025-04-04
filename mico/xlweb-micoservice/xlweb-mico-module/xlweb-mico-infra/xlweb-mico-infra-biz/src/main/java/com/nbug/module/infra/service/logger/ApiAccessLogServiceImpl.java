package com.nbug.module.infra.service.logger;

import cn.hutool.core.util.StrUtil;
import com.nbug.depends.tenant.core.context.TenantContextHolder;
import com.nbug.depends.tenant.core.util.TenantUtils;
import com.nbug.mico.common.model.logger.ApiAccessLog;
import com.nbug.mico.common.pojo.PageResult;
import com.nbug.mico.common.utils.object.BeanUtils;
import com.nbug.module.infra.api.logger.dto.ApiAccessLogCreateReqDTO;
import com.nbug.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import com.nbug.module.infra.dal.ApiAccessLogDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static com.nbug.mico.common.model.logger.ApiAccessLog.REQUEST_PARAMS_MAX_LENGTH;
import static com.nbug.mico.common.model.logger.ApiAccessLog.RESULT_MSG_MAX_LENGTH;


/**
 * API 访问日志 Service 实现类
 *
 * @author NUBG
 */
@Slf4j
@Service
@Validated
public class ApiAccessLogServiceImpl implements ApiAccessLogService {

    @Resource
    private ApiAccessLogDao apiAccessLogDao;

    @Override
    public void createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        ApiAccessLog apiAccessLog = BeanUtils.toBean(createDTO, ApiAccessLog.class);
        apiAccessLog.setRequestParams(StrUtil.maxLength(apiAccessLog.getRequestParams(), REQUEST_PARAMS_MAX_LENGTH));
        apiAccessLog.setResultMsg(StrUtil.maxLength(apiAccessLog.getResultMsg(), RESULT_MSG_MAX_LENGTH));
        if (TenantContextHolder.getTenantId() != null) {
            apiAccessLogDao.insert(apiAccessLog);
        } else {
            // 极端情况下，上下文中没有租户时，此时忽略租户上下文，避免插入失败！
            TenantUtils.executeIgnore(() -> apiAccessLogDao.insert(apiAccessLog));
        }
    }

    @Async
    @Override
    public void createApiAccessLogAsync(ApiAccessLogCreateReqDTO createDTO) {
        ApiAccessLog apiAccessLog = BeanUtils.toBean(createDTO, ApiAccessLog.class);
        apiAccessLog.setRequestParams(StrUtil.maxLength(apiAccessLog.getRequestParams(), REQUEST_PARAMS_MAX_LENGTH));
        apiAccessLog.setResultMsg(StrUtil.maxLength(apiAccessLog.getResultMsg(), RESULT_MSG_MAX_LENGTH));
        if (TenantContextHolder.getTenantId() != null) {
            apiAccessLogDao.insert(apiAccessLog);
        } else {
            // 极端情况下，上下文中没有租户时，此时忽略租户上下文，避免插入失败！
            TenantUtils.executeIgnore(() -> apiAccessLogDao.insert(apiAccessLog));
        }
    }

    @Override
    public PageResult<ApiAccessLog> getApiAccessLogPage(ApiAccessLogPageReqVO pageReqVO) {
        return apiAccessLogDao.selectPage(pageReqVO);
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public Integer cleanAccessLog(Integer exceedDay, Integer deleteLimit) {
        int count = 0;
        LocalDateTime expireDate = LocalDateTime.now().minusDays(exceedDay);
        // 循环删除，直到没有满足条件的数据
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            int deleteCount = apiAccessLogDao.deleteByCreateTimeLt(expireDate, deleteLimit);
            count += deleteCount;
            // 达到删除预期条数，说明到底了
            if (deleteCount < deleteLimit) {
                break;
            }
        }
        return count;
    }

}
