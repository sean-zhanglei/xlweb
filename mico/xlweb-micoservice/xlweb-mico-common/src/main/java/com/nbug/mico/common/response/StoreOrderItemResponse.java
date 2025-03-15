package com.nbug.mico.common.response;

import com.nbug.mico.common.vo.StoreOrderInfoOldVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="核销订单")
public class StoreOrderItemResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "订单ID")
    private Integer id;

    @Schema(description = "订单号")
    private String orderId;

    @Schema(description = "用户id")
    private Integer uid;

    @Schema(description = "用户姓名")
    private String realName;

    @Schema(description = "用户电话")
    private String userPhone;

    @Schema(description = "订单总价")
    private BigDecimal totalPrice;

    @Schema(description = "实际支付金额")
    private BigDecimal payPrice;

    @Schema(description = "支付状态")
    private Boolean paid;

    @Schema(description = "支付时间")
    private Date payTime;

    @Schema(description = "支付方式")
    private String payType;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "订单状态（-1 : 申请退款 -2 : 退货成功 0：待发货；1：待收货；2：已收货；3：待评价；-1：已退款）")
    private Integer status;

    @Schema(description = "门店名称")
    private String storeName;

    @Schema(description = "店员名称")
    private String clerkName;

    @Schema(description = "商品信息")
    private List<StoreOrderInfoOldVo> productList = new ArrayList<>();

    @Schema(description = "订单状态")
    private Map<String, String> statusStr;

    @Schema(description = "支付方式")
    private String payTypeStr;

    @Schema(description = "邮费")
    private BigDecimal totalPostage;

    @Schema(description = "支付邮费")
    private BigDecimal payPostage;

    @Schema(description = "消费赚取积分")
    private BigDecimal gainIntegral;

    @Schema(description = "使用积分")
    private BigDecimal useIntegral;

    @Schema(description = "给用户退了多少积分")
    private BigDecimal backIntegral;

    @Schema(description = "是否删除")
    private Boolean isDel;

    @Schema(description = "后台是否删除")
    private Boolean isSystemDel;

    @Schema(description = "用户备注")
    private String mark;

    @Schema(description = "管理员备注")
    private String remark;

    @Schema(description = "退款图片")
    private String refundReasonWapImg;

    @Schema(description = "退款用户说明")
    private String refundReasonWapExplain;

    @Schema(description = "退款时间")
    private Date refundReasonTime;

    @Schema(description = "前台退款原因")
    private String refundReasonWap;

    @Schema(description = "不退款的理由")
    private String refundReason;

    @Schema(description = "退款金额")
    private BigDecimal refundPrice;

    @Schema(description = "0 未退款 1 申请中 2 已退款")
    private Integer refundStatus;

    @Schema(description = "订单商品总数")
    private Integer totalNum;

    @Schema(description = "配送方式 1=快递 ，2=门店自提")
    private Integer shippingType;

    @Schema(description = "核销码")
    private String verifyCode;

    @Schema(description = "推广人信息")
    private StoreOrderSpreadInfoResponse spreadInfo = new StoreOrderSpreadInfoResponse();

    @Schema(description = "订单类型")
    private String orderType;
}
