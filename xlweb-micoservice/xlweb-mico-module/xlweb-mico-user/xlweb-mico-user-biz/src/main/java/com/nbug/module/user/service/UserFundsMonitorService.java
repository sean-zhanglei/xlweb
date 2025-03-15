package com.nbug.module.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.model.user.UserBrokerageRecord;
import com.nbug.mico.common.request.BrokerageRecordRequest;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.vo.UserFundsMonitor;

/**
*  UserRechargeService 接口

*/
public interface UserFundsMonitorService extends IService<UserFundsMonitor> {

    /**
     * 佣金记录
     * @param request 筛选条件
     * @param pageParamRequest 分页参数
     * @return PageInfo
     */
    PageInfo<UserBrokerageRecord> getBrokerageRecord(BrokerageRecordRequest request, PageParamRequest pageParamRequest);
}
