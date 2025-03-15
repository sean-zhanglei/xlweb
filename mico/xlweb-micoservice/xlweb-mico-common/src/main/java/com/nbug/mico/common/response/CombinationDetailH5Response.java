package com.nbug.mico.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *  拼团商品移动端对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="拼团商品移动端对象")
public class CombinationDetailH5Response implements Serializable {

    private static final long serialVersionUID = -885733985825623484L;

    @Schema(description = "拼团商品ID")
    private Integer id;

    @Schema(description = "商品id")
    private Integer productId;

    @Schema(description = "推荐图")
    private String image;

    @Schema(description = "轮播图")
    private String sliderImage;

    @Schema(description = "活动标题")
    private String storeName;

    @Schema(description = "参团人数")
    private Integer people;

    @Schema(description = "简介")
    private String storeInfo;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "单位名")
    private String unitName;

    @Schema(description = "限购总数")
    private Integer quota;

    @Schema(description = "限量总数显示")
    private Integer quotaShow;

    @Schema(description = "原价")
    private BigDecimal otPrice;

    @Schema(description = "每个订单可购买数量")
    private Integer onceNum;

    @Schema(description = "虚拟销量")
    private Integer ficti;

    @Schema(description = "商品详情")
    private String content;
}
