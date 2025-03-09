package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @program: xlweb
 * @author: 大粽子
 * @create: 2021-10-27 10:38
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "产品导出")
public class OrderExcelVo implements Serializable {

    @Schema(description = "订单号")
    private String orderId;

    @Schema(description = "实际支付金额")
    private String payPrice;

//    @Schema(description = "支付方式")
//    private String payType;

    @Schema(description = "创建时间")
    private String createTime;

//    @Schema(description = "订单状态（0：待发货；1：待收货；2：已收货，待评价；3：已完成；）")
//    private String status;

    @Schema(description = "商品信息")
    private String productName;

    @Schema(description = "订单状态")
    private String statusStr;

    @Schema(description = "支付方式")
    private String payTypeStr;

//    @Schema(description = "是否删除")
//    private String isDel;
//
//    @Schema(description = "退款图片")
//    private String refundReasonWapImg;
//
//    @Schema(description = "退款用户说明")
//    private String refundReasonWapExplain;
//
//    @Schema(description = "退款时间")
//    private String refundReasonTime;
//
//    @Schema(description = "前台退款原因")
//    private String refundReasonWap;
//
//    @Schema(description = "不退款的理由")
//    private String refundReason;
//
//    @Schema(description = "退款金额")
//    private String refundPrice;
//
//    @Schema(description = "0 未退款 1 申请中 2 已退款")
//    private String refundStatus;
//
//    @Schema(description = "核销码")
//    private String verifyCode;

    @Schema(description = "订单类型")
    private String orderType;

//    @Schema(description = "订单管理员备注")
//    private String remark;

    @Schema(description = "用户姓名")
    private String realName;

//    @Schema(description = "支付状态")
//    private String paid;
//
//    @Schema(description = "订单类型:0-普通订单，1-视频号订单")
//    private String type;
//
//    @Schema(description = "是否改价,0-否，1-是")
//    private String isAlterPrice;
}
