package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 砍价商品Request

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="砍价表")
public class StoreBargainRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "砍价商品id|新增时不填，修改时必填")
    private Integer id;

    @Schema(description = "关联商品ID")
    @NotNull(message = "商品编号不能为空")
    @Min(value = 1, message = "关联商品ID不能小于1")
    private Integer productId;

    @Schema(description = "砍价活动名称")
    @NotBlank(message = "砍价活动名称不能为空")
    @Length(max = 200, message = "砍价活动名称不能超过200个字符")
    private String title;

    @Schema(description = "砍价活动图片")
    @NotBlank(message = "砍价活动图片不能为空")
    @Length(max = 150, message = "砍价活动图片不能超过150个字符")
    private String image;

    @Schema(description = "单位名称")
    @NotBlank(message = "单位名称不能为空")
    @Length(max = 16, message = "单位名称不能超过16个字符")
    private String unitName;

    @Schema(description = "砍价商品轮播图")
    @NotBlank(message = "轮播图不能为空")
    @Length(max = 2000, message = "砍价商品轮播图不能超过2000个字符")
    private String images;

    @Schema(description = "砍价开启时间")
    @NotBlank(message = "砍价开启时间不能为空")
    private String startTime;

    @Schema(description = "砍价结束时间")
    @NotBlank(message = "砍价结束时间不能为空")
    private String stopTime;

    @Schema(description = "砍价商品名称")
    @NotBlank(message = "砍价商品名称不能为空")
    @Length(max = 200, message = "砍价商品名称不能超过200个字符")
    private String storeName;

    @Schema(description = "砍价金额")
    private BigDecimal price;

    @Schema(description = "砍价商品最低价")
    private BigDecimal minPrice;

    @Schema(description = "购买数量限制")
    @NotNull(message = "购买数量限制不能为空")
    @Min(value = 1, message = "购买数量限制必须大于0")
    private Integer num;

//    @Schema(description = "用户每次砍价的最大金额")
//    private BigDecimal bargainMaxPrice;

//    @Schema(description = "用户每次砍价的最小金额")
//    private BigDecimal bargainMinPrice;

    @Schema(description = "帮砍次数")
    @NotNull(message = "帮砍次数不能为空")
    @Min(value = 1, message = "帮砍次数必须大于0")
    private Integer bargainNum;

    @Schema(description = "砍价状态 0(到砍价时间不自动开启)  1(到砍价时间自动开启时间)")
    @NotNull(message = "砍价活动状态不能为空")
    private Boolean status;

//    @Schema(description = "砍价活动简介")
//    @NotBlank(message = "砍价活动简介不能为空")
//    private String info;

//    @Schema(description = "砍价规则")
//    private String rule;

    @Schema(description = "运费模板ID")
    @NotNull(message = "运费模板ID不能为空")
    private Integer tempId;

    @Schema(description = "砍价活动发起人数")
    @Min(value = 2, message = "砍价人数最少两人")
    @Max(value = 9999, message = "砍价人数最多9999")
    private Integer peopleNum;

    @Schema(description = "商品属性")
    @NotEmpty(message = "商品属性不能为空")
    private List<StoreProductAttrAddRequest> attr;

    @Schema(description = "商品属性详情")
    @NotEmpty(message = "商品属性详情不能为空")
    private List<StoreProductAttrValueAddRequest> attrValue;

    @Schema(description = "商品描述")
    private String content;
}
