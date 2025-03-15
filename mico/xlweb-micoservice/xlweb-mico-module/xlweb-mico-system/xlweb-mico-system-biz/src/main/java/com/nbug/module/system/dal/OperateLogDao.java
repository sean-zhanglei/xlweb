package com.nbug.module.system.dal;

import com.nbug.depends.mybatis.plus.core.mapper.BaseMapperX;
import com.nbug.mico.common.model.logger.OperateLog;
import com.nbug.mico.common.pojo.PageResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperateLogDao extends BaseMapperX<OperateLog> {

    default PageResult<OperateLog> selectPage(OperateLog pageReqDTO) {
//        return selectPage(pageReqDTO, new LambdaQueryWrapperX<OperateLog>()
//                .eqIfPresent(OperateLog::getUserId, pageReqDTO.getUserId())
//                .eqIfPresent(OperateLog::getBizId, pageReqDTO.getBizId())
//                .likeIfPresent(OperateLog::getType, pageReqDTO.getType())
//                .likeIfPresent(OperateLog::getSubType, pageReqDTO.getSubType())
//                .likeIfPresent(OperateLog::getAction, pageReqDTO.getAction())
//                .betweenIfPresent(OperateLog::getCreateTime, pageReqDTO.getCreateTime())
//                .orderByDesc(OperateLog::getId));
        return new PageResult<OperateLog>();
    }

//    default PageResult<OperateLog> selectPage(OperateLog pageReqDTO) {
//        return selectPage(pageReqDTO, new LambdaQueryWrapperX<OperateLog>()
//                .eqIfPresent(OperateLog::getType, pageReqDTO.getType())
//                .eqIfPresent(OperateLog::getBizId, pageReqDTO.getBizId())
//                .eqIfPresent(OperateLog::getUserId, pageReqDTO.getUserId())
//                .orderByDesc(OperateLog::getId));
//    }

}
