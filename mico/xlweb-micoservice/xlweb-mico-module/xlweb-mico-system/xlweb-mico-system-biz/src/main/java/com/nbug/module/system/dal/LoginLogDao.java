package com.nbug.module.system.dal;

import com.nbug.depends.mybatis.plus.core.mapper.BaseMapperX;
import com.nbug.mico.common.model.logger.LoginLog;
import com.nbug.mico.common.pojo.PageResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginLogDao extends BaseMapperX<LoginLog> {

    default PageResult<LoginLog> selectPage(LoginLog reqVO) {
//        LambdaQueryWrapperX<LoginLog> query = new LambdaQueryWrapperX<LoginLog>()
//                .likeIfPresent(LoginLog::getUserIp, reqVO.getUserIp())
//                .likeIfPresent(LoginLog::getUsername, reqVO.getUsername());
//                .betweenIfPresent(LoginLog::getCreateTime, reqVO.getCreateTime());
//        if (Boolean.TRUE.equals(reqVO.getStatus())) {
//            query.eq(LoginLog::getResult, LoginResultEnum.SUCCESS.getResult());
//        } else if (Boolean.FALSE.equals(reqVO.getStatus())) {
//            query.gt(LoginLog::getResult, LoginResultEnum.SUCCESS.getResult());
//        }
//        query.orderByDesc(LoginLog::getId); // 降序
//        return selectPage(reqVO, query);

        return new PageResult<LoginLog>();
    }

}
