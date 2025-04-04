package com.nbug.mico.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 生成订单Vo对象
 
 */
@Data
public class ShopOrderVo {

    /** 交易组件平台订单ID */
    @TableField(value = "order_id")
    private Integer orderId;

    /** 商家自定义订单ID */
    @TableField(value = "out_order_id")
    private String outOrderId;

    /** 订单状态：10-待付款，11-收银台支付完成（自动流转，对商家来说和10同等对待即可），20-待发货，30-待收货，100-完成，200-全部商品售后之后，订单取消，250-用户主动取消/待付款超时取消/商家取消 */
    private Integer status;

    /** 商家小程序该订单的页面path，用于微信侧订单中心跳转 */
    private String path;

    /** 订单详情 */
    @TableField(value = "order_detail")
    private ShopOrderDetailAddVo orderDetail;
}
