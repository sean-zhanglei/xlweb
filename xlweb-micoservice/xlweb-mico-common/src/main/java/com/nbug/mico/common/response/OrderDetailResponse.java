package com.nbug.mico.common.response;

import com.nbug.mico.common.vo.StoreOrderInfoOldVo;
import com.nbug.mico.common.model.order.StoreOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单列表响应对象
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="订单列表响应对象")
public class OrderDetailResponse implements Serializable {

    private static final long serialVersionUID = 1387727608277207652L;

    private StoreOrder storeOrder;
    private List<StoreOrderInfoOldVo> cartInfo;
    private String statusPic;
    private Integer offlinePayStatus;


    @Schema(description = "订单id")
    private Integer id;

    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "支付状态")
    private Boolean paid;

    @Schema(description = "支付时间")
    private Date payTime;

    @Schema(description = "支付金额")
    private BigDecimal payPrice;

    @Schema(description = "订单状态（0：待发货；1：待收货；2：已收货，待评价；3：已完成；）")
    private Integer status;

    @Schema(description = "订单状态")
    private String orderStatus;

    @Schema(description = "订单商品总数")
    private Integer totalNum;

    @Schema(description = "支付邮费")
    private BigDecimal payPostage;

    @Schema(description = "0 未退款 1 申请中 2 已退款 3 退款中")
    private Integer refundStatus;

    @Schema(description = "快递名称/送货人姓名")
    private String deliveryName;

    @Schema(description = "发货类型")
    private String deliveryType;

    @Schema(description = "快递单号/手机号")
    private String deliveryId;

    @Schema(description = "拼团id 0没有拼团")
    private Integer pinkId;

    @Schema(description = "砍价id")
    private Integer bargainId;

    @Schema(description = "核销码")
    private String verifyCode;

    @Schema(description = "门店id")
    private Integer storeId;

    @Schema(description = "配送方式 1=快递 ，2=门店自提")
    private Integer shippingType;

    @Schema(description = "活动类型")
    private String activityType;

    @Schema(description = "订单详情对象列表")
    private List<OrderInfoResponse> orderInfoList;

    @Schema(description = "订单类型:0-普通订单，1-视频号订单")
    private Integer type;
}
