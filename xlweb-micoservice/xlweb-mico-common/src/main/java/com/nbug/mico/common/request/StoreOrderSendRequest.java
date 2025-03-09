package com.nbug.mico.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 订单发货对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="订单发货对象")
public class StoreOrderSendRequest {
    private static final long serialVersionUID=1L;

    @Schema(description = "订单id")
    private Integer id;

    @Schema(description = "订单编号")
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "类型， 1发货，2送货，3虚拟", allowableValues = "range[1,2,3]")
    @NotBlank(message = "请选择类型")
    private String type;

    @Schema(description = "快递公司名,发货类型必传")
    private String expressName;

    @Schema(description = "快递公司编码,发货类型必传")
    private String expressCode;

    @Schema(description = "快递单号,发货类型必传")
    private String expressNumber;

    @Schema(description = "发货记录类型，1正常、2电子面单,发货类型必传")
    private String expressRecordType;

    @Schema(description = "电子面单模板,电子面单必传")
    private String expressTempId;

    @Schema(description = "寄件人姓名,电子面单必传")
    private String toName;

    @Schema(description = "寄件人电话,电子面单必传")
    private String toTel;

    @Schema(description = "寄件人地址,电子面单必传")
    private String toAddr;

    @Schema(description = "送货人姓名,送货类型必传")
    private String deliveryName;

    @Schema(description = "送货人电话,送货类型必传")
    private String deliveryTel;
}
