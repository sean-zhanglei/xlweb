package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品排行响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="商品排行响应对象")
public class ProductRankingResponse implements Serializable {

    private static final long serialVersionUID = 3362714265772774491L;

    @Schema(description = "顺序")
    private Integer sort;

    @Schema(description = "商品id")
    private Integer productId;

    @Schema(description = "浏览量")
    private Integer pageView;

    @Schema(description = "收藏量")
    private Integer collectNum;

    @Schema(description = "加购件数")
    private Integer addCartNum;

    @Schema(description = "下单商品数（销售件数）")
    private Integer salesNum;

    @Schema(description = "销售额")
    private BigDecimal salesAmount;

    @Schema(description = "商品名称")
    private String proName;

    @Schema(description = "商品图片")
    private String image;
}
