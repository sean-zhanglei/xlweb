package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 砍价商品详情响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="砍价商品详情响应对象")
public class BargainDetailH5Response implements Serializable {

    private static final long serialVersionUID = 4177599369617161973L;

    @Schema(description = "砍价商品ID")
    private Integer id;

    @Schema(description = "关联商品ID")
    private Integer productId;

    @Schema(description = "砍价活动名称")
    private String title;

    @Schema(description = "砍价活动图片")
    private String image;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "砍价开启时间")
    private Long startTime;

    @Schema(description = "砍价结束时间")
    private Long stopTime;

    @Schema(description = "砍价金额")
    private BigDecimal price;

    @Schema(description = "砍价商品最低价")
    private BigDecimal minPrice;

    @Schema(description = "购买数量限制")
    // 单个活动每个用户发起砍价次数限制
    private Integer num;

    @Schema(description = "砍价活动简介")
    private String info;

//    @Schema(description = "砍价规则")
//    private String rule;

    @Schema(description = "限购总数")
    private Integer quota;

    @Schema(description = "限量总数显示")
    private Integer quotaShow;

    @Schema(description = "商品规格sku")
    private String sku;

    @Schema(description = "商品规格属性id")
    private Integer attrValueId;

    @Schema(description = "商品详情")
    private String content;

    @Schema(description = "主商品状态:normal-正常，sellOut-售罄，soldOut-下架,delete-删除")
    private String masterStatus;

}
