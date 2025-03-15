package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品ExcelVo对象类

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "产品导出")
public class ProductExcelVo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "商品名称")
    private String storeName;

    @Schema(description = "商品简介")
    private String storeInfo;

    @Schema(description = "商品分类")
    private String cateName;

    @Schema(description = "价格")
    private String price;

    @Schema(description = "库存")
    private String stock;

    @Schema(description = "销量")
    private String sales;

    @Schema(description = "浏览量")
    private String browse;
}
