package com.nbug.mico.common.response;

import com.baomidou.mybatisplus.annotation.TableField;
import com.nbug.mico.common.vo.StoreOrderInfoOldVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 核销订单确认前的数据

 */
@Data
public class StoreOrderVerificationConfirmResponse {

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

    @Schema(description = "详细地址")
    private String userAddress;

    @Schema(description = "运费金额")
    private BigDecimal freightPrice;

    @Schema(description = "订单商品总数")
    private Integer totalNum;

    @Schema(description = "订单总价")
    private BigDecimal totalPrice;

    @Schema(description = "邮费")
    private BigDecimal totalPostage;

    @Schema(description = "实际支付金额")
    private BigDecimal payPrice;

    @Schema(description = "支付邮费")
    private BigDecimal payPostage;

    @Schema(description = "抵扣金额")
    private BigDecimal deductionPrice;

    @Schema(description = "优惠券id")
    private Integer couponId;

    @Schema(description = "优惠券金额")
    private BigDecimal couponPrice;

    @Schema(description = "支付状态")
    private Boolean paid;

    @Schema(description = "支付时间")
    private Date payTime;

    @Schema(description = "支付方式")
    private String payType;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "订单状态（-1 : 申请退款 -2 : 退货成功 0：待发货；1：待收货；2：已收货，待评价；3：已完成；）")
    private Integer status;

    @Schema(description = "0 未退款 1 申请中 2 已退款")
    private Integer refundStatus;

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

    @Schema(description = "快递名称/送货人姓名")
    private String deliveryName;

    @Schema(description = "发货类型")
    private String deliveryType;

    @Schema(description = "快递单号/手机号")
    private String deliveryId;

    @Schema(description = "消费赚取积分")
    private Integer gainIntegral;

    @Schema(description = "使用积分")
    private Integer useIntegral;

    @Schema(description = "给用户退了多少积分")
    private Integer backIntegral;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "是否删除")
    private Boolean isDel;

    @Schema(description = "唯一id(md5加密)类似id")
    @TableField(value = "`unique`")
    private String unique;

    @Schema(description = "管理员备注")
    private String remark;

    @Schema(description = "商户ID")
    private Integer merId;

    private Integer isMerCheck;

    @Schema(description = "拼团商品id0一般商品")
    private Integer combinationId;

    @Schema(description = "拼团id 0没有拼团")
    private Integer pinkId;

    @Schema(description = "成本价")
    private BigDecimal cost;

    @Schema(description = "秒杀商品ID")
    private Integer seckillId;

    @Schema(description = "砍价id")
    private Integer bargainId;

    @Schema(description = "核销码")
    private String verifyCode;

    @Schema(description = "门店id")
    private Integer storeId;

    @Schema(description = "配送方式 1=快递 ，2=门店自提")
    private Integer shippingType;

    @Schema(description = "店员id")
    private Integer clerkId;

    @Schema(description = "支付渠道(0微信公众号1微信小程序)")
    private int isChannel;

    @Schema(description = "消息提醒")
    private Boolean isRemind;

    @Schema(description = "后台是否删除")
    private Boolean isSystemDel;

    @Schema(description = "订单详情")
    private List<StoreOrderInfoOldVo> storeOrderInfoVos;
}
