package com.nbug.mico.common.response;

import com.nbug.mico.common.request.StoreProductAttrValueRequest;
import com.nbug.mico.common.model.product.StoreProductAttr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 砍价商品响应体

 */
@Data
public class StoreBargainResponse {

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

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "砍价商品轮播图")
    private String images;

    @Schema(description = "砍价开启时间")
    private String startTime;

    @Schema(description = "砍价结束时间")
    private String stopTime;

    @Schema(description = "砍价商品名称")
    private String storeName;

    @Schema(description = "砍价金额")
    private BigDecimal price;

    @Schema(description = "砍价商品最低价")
    private BigDecimal minPrice;

    @Schema(description = "每次购买的砍价商品数量")
    private Integer num;

    @Schema(description = "用户每次砍价的最大金额")
    private BigDecimal bargainMaxPrice;

    @Schema(description = "用户每次砍价的最小金额")
    private BigDecimal bargainMinPrice;

    @Schema(description = "用户每次砍价的次数")
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
    private String addTime;

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

    @Schema(description = "限量剩余")
    private Integer surplusQuota;

    @Schema(description = "帮助砍价好友人数")
    private Integer peopleNum;

    @Schema(description = "参与人数")
    private Long countPeopleAll;

    @Schema(description = "帮忙砍价人数")
    private Long countPeopleHelp;

    @Schema(description = "砍价成功人数")
    private Long countPeopleSuccess;

    @Schema(description = "商品属性")
    private List<StoreProductAttr> attr;

    @Schema(description = "商品属性详情")
    private List<StoreProductAttrValueRequest> attrValue;

}
