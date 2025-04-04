package com.nbug.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.common.request.AdminIntegralSearchRequest;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.response.UserIntegralRecordResponse;
import com.github.pagehelper.PageInfo;
import com.nbug.common.model.user.UserIntegralRecord;
import com.nbug.common.vo.MyRecord;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 用户积分记录Service

 */
public interface UserIntegralRecordService extends IService<UserIntegralRecord> {

    /**
     * 根据订单编号、uid获取记录列表
     * @param orderNo 订单编号
     * @param uid 用户uid
     * @return 记录列表
     */
    List<UserIntegralRecord> findListByOrderIdAndUid(String orderNo, Integer uid);

    /**
     * 积分解冻
     */
    void integralThaw();

    /**
     * PC后台列表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     * @return 记录列表
     */
    PageInfo<UserIntegralRecordResponse> findAdminList(AdminIntegralSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 根据类型条件计算积分总数
     * @param uid 用户uid
     * @param type 类型：1-增加，2-扣减
     * @param date 日期
     * @param linkType 关联类型
     * @return 积分总数
     */
    Integer getSumIntegral(Integer uid, Integer type, String date, String linkType);

    /**
     * H5用户积分列表
     * @param uid 用户uid
     * @param pageParamRequest 分页参数
     * @return List
     */
    List<UserIntegralRecord> findUserIntegralRecordList(Integer uid, PageParamRequest pageParamRequest);

    /**
     * 获取用户冻结的积分
     * @param uid 用户uid
     * @return 积分数量
     */
    Integer getFrozenIntegralByUid(Integer uid);

    MyRecord getIntegralBasic(String time);

    MyRecord getIntegralTrend(String time);

    MyRecord getIntegralChannel(String time);

    MyRecord getIntegralType(String time);
}
