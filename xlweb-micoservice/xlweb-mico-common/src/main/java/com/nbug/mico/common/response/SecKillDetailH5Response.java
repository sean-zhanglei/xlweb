package com.nbug.mico.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  秒杀商品移动端对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="秒杀商品移动端对象")
public class SecKillDetailH5Response implements Serializable {

    private static final long serialVersionUID = -885733985825623484L;

    @Schema(description = "商品秒杀产品表id")
    private Integer id;

    @Schema(description = "商品id")
    private Integer productId;

    @Schema(description = "推荐图")
    private String image;

    @Schema(description = "轮播图")
    private String sliderImage;

    @Schema(description = "秒杀商品名称")
    private String storeName;

    @Schema(description = "简介")
    private String storeInfo;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "原价")
    private BigDecimal otPrice;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "单位名")
    private String unitName;

    @Schema(description = "内容")
    private String description;

    @Schema(description = "开始时间")
    private Date startTime;

    @Schema(description = "结束时间")
    private Date stopTime;

    @Schema(description = "最多秒杀几个")
    private Integer num;

    @Schema(description = "时间段ID")
    private Integer timeId;

    @Schema(description = "限购总数 随销量递减")
    private Integer quota;

    @Schema(description = "限购总数显示 不变")
    private Integer quotaShow;

    @Schema(description = "虚拟销量")
    private Integer ficti;

    @Schema(description = "秒杀状态:0-关闭，1-即将开始，2-进行中，-1 - 已结束")
    private Integer seckillStatus;

    @Schema(description = "商品详情")
    private String content;

    @Schema(description = "结束时间（前端用）")
    private String timeSwap;
}
