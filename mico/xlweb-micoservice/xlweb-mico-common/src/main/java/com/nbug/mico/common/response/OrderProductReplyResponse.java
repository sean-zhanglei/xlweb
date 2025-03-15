package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 评价商品页响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="评价商品页响应对象")
public class OrderProductReplyResponse implements Serializable {

    private static final long serialVersionUID = -1926585407216207845L;

    @Schema(description = "购买数量")
    private Integer cartNum;

    @Schema(description = "价格")
    private BigDecimal truePrice;

    @Schema(description = "商品名称")
    private String storeName;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "商品编号")
    private Integer productId;

    @Schema(description = "规格sku")
    private String sku;

}
