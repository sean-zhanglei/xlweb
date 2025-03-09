package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *  秒杀商品移动端对象
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="秒杀商品移动端对象")
public class StoreSecKillH5Response implements Serializable {

    private static final long serialVersionUID = -885733985825623484L;

    @Schema(description = "商品秒杀产品表id")
    private Integer id;

    @Schema(description = "推荐图")
    private String image;

    @Schema(description = "活动标题")
    private String title;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "原价")
    private BigDecimal otPrice;

    @Schema(description = "单位名")
    private String unitName;

    @Schema(description = "限购总数")
    private Integer quota;

    @Schema(description = "已抢百分比")
    private Integer percent;
}
