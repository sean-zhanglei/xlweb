package com.nbug.mico.common.model.product;

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
 * 商品表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_product")
@Schema(description="商品表")
public class StoreProduct implements Serializable {

    private static final long serialVersionUID=1L;

    public StoreProduct() {
    }

    @Schema(description = "商品id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "商户Id(0为总后台管理员创建,不为0的时候是商户后台创建)")
    private Integer merId;

    @Schema(description = "商品图片")
    private String image;

    @Schema(description = "轮播图")
    private String sliderImage;

    @Schema(description = "商品名称")
    private String storeName;

    @Schema(description = "商品简介")
    private String storeInfo;

    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "商品条码（一维码）")
    private String barCode;

    @Schema(description = "分类id")
    private String cateId;

    @Schema(description = "商品价格")
    private BigDecimal price;

    @Schema(description = "会员价格")
    private BigDecimal vipPrice;

    @Schema(description = "市场价")
    private BigDecimal otPrice;

    @Schema(description = "邮费")
    private BigDecimal postage;

    @Schema(description = "单位名")
    private String unitName;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "状态（0：未上架，1：上架）")
    private Boolean isShow;

    @Schema(description = "是否热卖")
    private Boolean isHot;

    @Schema(description = "是否优惠")
    private Boolean isBenefit;

    @Schema(description = "是否精品")
    private Boolean isBest;

    @Schema(description = "是否新品")
    private Boolean isNew;

    @Schema(description = "添加时间")
    private Integer addTime;

    @Schema(description = "是否包邮")
    private Boolean isPostage;

    @Schema(description = "是否回收站")
    private Boolean isRecycle;

    @Schema(description = "是否删除")
    private Boolean isDel;

    @Schema(description = "商户是否代理 0不可代理1可代理")
    private Boolean merUse;

    @Schema(description = "获得积分")
    private Integer giveIntegral;

    @Schema(description = "成本价")
    private BigDecimal cost;

    @Schema(description = "秒杀状态 0 未开启 1已开启")
    private Boolean isSeckill;

    @Schema(description = "砍价状态 0未开启 1开启")
    private Boolean isBargain;

    @Schema(description = "是否优品推荐")
    private Boolean isGood;

    @Schema(description = "是否单独分佣")
    private Boolean isSub;

    @Schema(description = "虚拟销量")
    private Integer ficti;

    @Schema(description = "浏览量")
    private Integer browse;

    @Schema(description = "商品二维码地址(用户小程序海报)")
    private String codePath;

    @Schema(description = "淘宝京东1688类型")
    private String soureLink;

    @Schema(description = "主图视频链接")
    private String videoLink;

    @Schema(description = "运费模板ID")
    private Integer tempId;

    @Schema(description = "规格 0单 1多")
    private Boolean specType;

    @Schema(description = "活动显示排序0=默认，1=秒杀，2=砍价，3=拼团")
    private String activity;

    @Schema(description = "展示图")
    private String flatPattern;

    @Schema(description = "商品详情")
    @TableField(exist = false)
    private String content;
}
