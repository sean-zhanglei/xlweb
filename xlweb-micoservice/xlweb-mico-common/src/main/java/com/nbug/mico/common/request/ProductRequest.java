package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品搜索

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="商品搜索")
public class ProductRequest implements Serializable {

    private static final long serialVersionUID = 3481659942630712958L;

    @Schema(description = "搜索关键字")
    private String keyword;

    @Schema(description = "分类id")
    private Integer cid;

    @Schema(description = "价格排序", allowableValues = "range[asc,desc]")
    private String priceOrder;

    @Schema(description = "销量排序", allowableValues = "range[asc,desc]")
    private String salesOrder;

    @Schema(description = "是否新品")
    private Boolean news;
}
