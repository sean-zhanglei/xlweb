package com.nbug.module.store.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nbug.mico.common.model.order.StoreOrderStatus;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 订单操作记录表 Mapper 接口
 */
public interface StoreOrderStatusDao extends BaseMapper<StoreOrderStatus> {

    /**
     * 通过日期获取订单退款金额
     * @param date 日期，yyyy-MM-dd格式
     * @return BigDecimal
     */
    BigDecimal getRefundOrderAmountByDate(@Param("date") String date);

}
