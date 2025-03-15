package com.nbug.mico.common.model.bargain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 砍价表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_bargain")
@Schema(description="砍价表")
public class StoreBargain implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "砍价商品ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "关联商品ID")
    private Integer productId;

    @Schema(description = "砍价活动名称")
    private String title;

    @Schema(description = "砍价活动图片")
    private String image;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "砍价商品轮播图")
    private String images;

    @Schema(description = "砍价开启时间")
    private Long startTime;

    @Schema(description = "砍价结束时间")
    private Long stopTime;

    @Schema(description = "砍价商品名称")
    private String storeName;

    @Schema(description = "砍价金额")
    private BigDecimal price;

    @Schema(description = "砍价商品最低价")
    private BigDecimal minPrice;

    @Schema(description = "购买数量限制")
    // 单个活动每个用户发起砍价次数限制
    private Integer num;

    @Schema(description = "用户每次砍价的最大金额")
    private BigDecimal bargainMaxPrice;

    @Schema(description = "用户每次砍价的最小金额")
    private BigDecimal bargainMinPrice;

    @Schema(description = "帮砍次数")
    // 单个商品用户可以帮砍的次数，例：次数设置为1，甲和乙同时将商品A的砍价链接发给丙，丙只能帮甲或乙其中一个人砍价
    private Integer bargainNum;

    @Schema(description = "砍价状态 0(到砍价时间不自动开启)  1(到砍价时间自动开启时间)")
    private Boolean status;

    @Schema(description = "反多少积分")
    private Integer giveIntegral;

    @Schema(description = "砍价活动简介")
    private String info;

    @Schema(description = "成本价")
    private BigDecimal cost;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否推荐0不推荐1推荐")
    private Boolean isHot;

    @Schema(description = "是否删除 0未删除 1删除")
    private Boolean isDel;

    @Schema(description = "添加时间")
    private Long addTime;

    @Schema(description = "是否包邮 0不包邮 1包邮")
    private Boolean isPostage;

    @Schema(description = "邮费")
    private BigDecimal postage;

    @Schema(description = "砍价规则")
    private String rule;

    @Schema(description = "砍价商品浏览量")
    private Integer look;

    @Schema(description = "砍价商品分享量")
    private Integer share;

    @Schema(description = "运费模板ID")
    private Integer tempId;

    @Schema(description = "重量")
    private BigDecimal weight;

    @Schema(description = "体积")
    private BigDecimal volume;

    @Schema(description = "限购总数")
    private Integer quota;

    @Schema(description = "限量总数显示")
    private Integer quotaShow;

    @Schema(description = "帮助砍价好友人数")
    private Integer peopleNum;
}
