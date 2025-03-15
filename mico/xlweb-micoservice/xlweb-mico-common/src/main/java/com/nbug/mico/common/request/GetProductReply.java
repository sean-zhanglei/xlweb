package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 获取商品评论对象

 */
@Data
public class GetProductReply {

    @Schema(description = "商品attrid")
    @NotBlank(message = "商品uniId不能为空")
    private String uni;

    @Schema(description = "订单id")
    @NotNull(message = "订单id不能为空")
    private Integer orderId;
}
