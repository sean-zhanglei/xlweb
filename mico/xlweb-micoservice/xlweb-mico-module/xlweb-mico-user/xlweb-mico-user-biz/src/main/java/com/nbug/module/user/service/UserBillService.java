package com.nbug.module.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.model.user.UserBill;
import com.nbug.mico.common.request.FundsMonitorRequest;
import com.nbug.mico.common.request.FundsMonitorSearchRequest;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreOrderRefundRequest;
import com.nbug.mico.common.response.MonitorResponse;
import com.nbug.mico.common.vo.MyRecord;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * UserBillService 接口实现

 */
public interface UserBillService extends IService<UserBill> {

    /**
     * 列表
     *
     * @param request          请求参数
     * @param pageParamRequest 分页类参数
     * @return List<UserBill>
     */
    List<UserBill> getList(FundsMonitorSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 列表
     *
     * @param request          请求参数
     * @param pageParamRequest 分页类参数
     * @return List<UserBill>
     */
    MyRecord getBalanceList(FundsMonitorSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 新增/消耗  总金额
     *
     * @param pm       Integer 0 = 支出 1 = 获得
     * @param userId   Integer 用户uid
     * @param category String 类型
     * @param date     String 时间范围
     * @param type     String 小类型
     * @return UserBill
     */
    BigDecimal getSumBigDecimal(Integer pm, Integer userId, String category, String date, String type);

    /**
     * 保存退款日志
     *
     * @return boolean
     */
    Boolean saveRefundBill(StoreOrderRefundRequest request, User user);

    /**
     * 资金监控
     *
     * @param request          查询参数
     * @param pageParamRequest 分页参数
     * @return PageInfo
     */
    PageInfo<MonitorResponse> fundMonitoring(FundsMonitorRequest request, PageParamRequest pageParamRequest);

    /**
     * 用户账单记录（现金）
     *
     * @param uid  用户uid
     * @param type 记录类型：all-全部，expenditure-支出，income-收入
     * @return PageInfo
     */
    PageInfo<UserBill> nowMoneyBillRecord(Integer uid, String type, PageParamRequest pageRequest);

    MyRecord getBalanceBasic(String time);

    MyRecord getBalanceTrend(String time);

    MyRecord getBalanceChannel(String time);

    MyRecord getBalanceType(String time);

    MyRecord getFlowRecord(String type, String time, PageParamRequest pageParamRequest);

    MyRecord getFlowList(List<Integer> ids, String keywords, PageParamRequest pageParamRequest);

    List<Map<String, Object>> listMaps(String timeType, Date startDate, Date  endDate);
}
