package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 商品添加对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_product")
@Schema( description="商品添加对象")
public class StoreProductAddRequest implements Serializable {

    private static final long serialVersionUID = -452373239606480650L;

    @Schema(description = "商品id|添加时不填，修改时必填")
    private Integer id;

    @Schema(description = "商品图片", required = true)
    @NotBlank(message = "商品图片不能为空")
    @Length(max = 255, message = "商品图片名称长度不能超过255个字符")
    private String image;

    @Schema(description = "轮播图", required = true)
    @NotBlank(message = "轮播图不能为空")
    @Length(max = 2000, message = "轮播图名称长度不能超过2000个字符")
    private String sliderImage;

    @Schema(description = "商品名称", required = true)
    @NotBlank(message = "商品名称不能为空")
    @Length(max = 128, message = "商品名称长度不能超过128个字符")
    private String storeName;

    @Schema(description = "商品简介", required = true)
    @NotBlank(message = "商品简介不能为空")
    @Length(max = 256, message = "商品简介长度不能超过256个字符")
    private String storeInfo;

    @Schema(description = "关键字", required = true)
    @Length(max = 255, message = "关键字长度不能超过255个字符")
    @NotBlank(message = "关键字不能为空")
    private String keyword;

    @Schema(description = "分类id|逗号分隔", required = true)
    @NotBlank(message = "商品分类不能为空")
    @Length(max = 64, message = "商品分类组合长度不能超过64个字符")
    private String cateId;

    @Schema(description = "单位名", required = true)
    @NotBlank(message = "单位名称不能为空")
    @Length(max = 32, message = "单位名长度不能超过32个字符")
    private String unitName;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否热卖")
    private Boolean isHot;

    @Schema(description = "是否优惠")
    private Boolean isBenefit;

    @Schema(description = "是否精品")
    private Boolean isBest;

    @Schema(description = "是否新品")
    private Boolean isNew;

    @Schema(description = "是否优品推荐")
    private Boolean isGood;

    @Schema(description = "获得积分")
    private Integer giveIntegral;

    @Schema(description = "是否单独分佣", required = true)
    @NotNull(message = "是否单独分佣不能为空")
    private Boolean isSub;

    @Schema(description = "虚拟销量")
    private Integer ficti;

    @Schema(description = "运费模板ID", required = true)
    @NotNull(message = "运费模板不能为空")
    private Integer tempId;

    @Schema(description = "规格 0单 1多", required = true)
    @NotNull(message = "商品规格类型不能为空")
    private Boolean specType;

    @Schema(description = "活动显示排序 0=默认，1=秒杀，2=砍价，3=拼团")
    private List<String> activity;

    @Schema(description = "商品属性", required = true)
    @NotEmpty(message = "商品属性不能为空")
    private List<StoreProductAttrAddRequest> attr;

    @Schema(description = "商品属性详情", required = true)
    @NotEmpty(message = "商品属性详情不能为空")
    private List<StoreProductAttrValueAddRequest> attrValue;

    @Schema(description = "商品描述")
    private String content;

    @Schema(description = "优惠券id集合")
    private List<Integer> couponIds;

    @Schema(description = "展示图")
    @Length(max = 1000, message = "展示图名称长度不能超过1000个字符")
    private String flatPattern;
}
