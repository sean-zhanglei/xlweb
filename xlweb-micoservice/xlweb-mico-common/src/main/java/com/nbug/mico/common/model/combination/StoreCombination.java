package com.nbug.mico.common.model.combination;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 拼团商品表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_combination")
@Schema(description="拼团商品表")
public class StoreCombination implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "拼团商品ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "商品id")
    private Integer productId;

    @Schema(description = "商户id")
    private Integer merId;

    @Schema(description = "推荐图")
    private String image;

    @Schema(description = "轮播图")
    private String images;

    @Schema(description = "活动标题")
    private String title;

    @Schema(description = "活动属性")
    private String attr;

    @Schema(description = "参团人数")
    private Integer people;

    @Schema(description = "简介")
    private String info;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "添加时间")
    private Long addTime;

    @Schema(description = "推荐")
    private Boolean isHost;

    @Schema(description = "商品状态")
    private Boolean isShow;

    private Boolean isDel;

    private Boolean combination;

    @Schema(description = "商户是否可用1可用0不可用")
    private Boolean merUse;

    @Schema(description = "是否包邮1是0否")
    private Boolean isPostage;

    @Schema(description = "邮费")
    private BigDecimal postage;

    @Schema(description = "拼团开始时间")
    private Long startTime;

    @Schema(description = "拼团结束时间")
    private Long stopTime;

    @Schema(description = "拼团订单有效时间(小时)")
    private Integer effectiveTime;

    @Schema(description = "拼图商品成本")
    private BigDecimal cost;

    @Schema(description = "浏览量")
    private Integer browse;

    @Schema(description = "单位名")
    private String unitName;

    @Schema(description = "运费模板ID")
    private Integer tempId;

    @Schema(description = "重量")
    private BigDecimal weight;

    @Schema(description = "体积")
    private BigDecimal volume;

    @Schema(description = "单次购买数量")
    private Integer num;

    @Schema(description = "限购总数")
    private Integer quota;

    @Schema(description = "限量总数显示")
    private Integer quotaShow;

    @Schema(description = "原价")
    private BigDecimal otPrice;

    @Schema(description = "每个订单可购买数量")
    private Integer onceNum;

    @Schema(description = "虚拟成团百分比")
    private Integer virtualRation;

    @Schema(description = "限量百分比")
    @TableField(exist = false)
    private Integer quotaPercent;
}
