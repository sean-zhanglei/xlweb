package com.nbug.module.infra.dal;

import com.nbug.depends.mybatis.plus.core.mapper.BaseMapperX;
import com.nbug.depends.mybatis.plus.core.query.LambdaQueryWrapperX;
import com.nbug.mico.common.model.logger.ApiErrorLog;
import com.nbug.mico.common.pojo.PageResult;
import com.nbug.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * API 错误日志 Mapper
 *
 * @author NBUG
 */
@Mapper
public interface ApiErrorLogDao extends BaseMapperX<ApiErrorLog> {

    default PageResult<ApiErrorLog> selectPage(ApiErrorLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ApiErrorLog>()
                .eqIfPresent(ApiErrorLog::getUserId, reqVO.getUserId())
                .eqIfPresent(ApiErrorLog::getUserType, reqVO.getUserType())
                .eqIfPresent(ApiErrorLog::getApplicationName, reqVO.getApplicationName())
                .likeIfPresent(ApiErrorLog::getRequestUrl, reqVO.getRequestUrl())
                .betweenIfPresent(ApiErrorLog::getExceptionTime, reqVO.getExceptionTime())
                .eqIfPresent(ApiErrorLog::getProcessStatus, reqVO.getProcessStatus())
                .orderByDesc(ApiErrorLog::getId)
        );
    }

    /**
     * 物理删除指定时间之前的日志
     *
     * @param createTime 最大时间
     * @param limit 删除条数，防止一次删除太多
     * @return 删除条数
     */
    @Delete("DELETE FROM eb_infra_api_error_log WHERE create_time < #{createTime} LIMIT #{limit}")
    Integer deleteByCreateTimeLt(@Param("createTime") LocalDateTime createTime, @Param("limit")Integer limit);

}
