package com.nbug.module.infra.dal;

import com.nbug.depends.mybatis.plus.core.mapper.BaseMapperX;
import com.nbug.depends.mybatis.plus.core.query.LambdaQueryWrapperX;
import com.nbug.mico.common.model.logger.ApiAccessLog;
import com.nbug.mico.common.pojo.PageResult;
import com.nbug.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * API 访问日志 Mapper
 *
 * @author NBUG
 */
@Mapper
public interface ApiAccessLogDao extends BaseMapperX<ApiAccessLog> {

    default PageResult<ApiAccessLog> selectPage(ApiAccessLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ApiAccessLog>()
                .eqIfPresent(ApiAccessLog::getUserId, reqVO.getUserId())
                .eqIfPresent(ApiAccessLog::getUserType, reqVO.getUserType())
                .eqIfPresent(ApiAccessLog::getApplicationName, reqVO.getApplicationName())
                .likeIfPresent(ApiAccessLog::getRequestUrl, reqVO.getRequestUrl())
                .betweenIfPresent(ApiAccessLog::getBeginTime, reqVO.getBeginTime())
                .geIfPresent(ApiAccessLog::getDuration, reqVO.getDuration())
                .eqIfPresent(ApiAccessLog::getResultCode, reqVO.getResultCode())
                .orderByDesc(ApiAccessLog::getId)
        );
    }

    /**
     * 物理删除指定时间之前的日志
     *
     * @param createTime 最大时间
     * @param limit 删除条数，防止一次删除太多
     * @return 删除条数
     */
    @Delete("DELETE FROM eb_infra_api_access_log WHERE create_time < #{createTime} LIMIT #{limit}")
    Integer deleteByCreateTimeLt(@Param("createTime") LocalDateTime createTime, @Param("limit") Integer limit);

}
