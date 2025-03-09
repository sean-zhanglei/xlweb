package com.nbug.mico.common.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nbug.mico.common.model.coupon.StoreCoupon;
import com.nbug.mico.common.model.product.StoreProductAttr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 商品表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_product")
@Schema(description="商品表")
public class StoreProductResponse implements Serializable {

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

    @Schema(description = "分类中文")
    private String cateValues;

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

    @Schema(description = "活动显示排序 0=默认，1=秒杀，2=砍价，3=拼团")
    private String activity;

    @Schema(description = "活动显示排序 0=默认，1=秒杀，2=砍价，3=拼团")
    private String activityStr;

    @Schema(description = "为移动端特定参数")
    private ProductActivityItemResponse activityH5;

    @Schema(description = "为移动端特定参数 所有参与的活动")
    private List<ProductActivityItemResponse> activityAllH5;

    @Schema(description = "商品属性")
    private List<StoreProductAttr> attr;

    @Schema(description = "商品属性详情")
    private List<StoreProductAttrValueResponse> attrValue;

    @Schema(description = "管理端用于映射attrResults")
    private List<HashMap<String,Object>> attrValues;

    private Integer[] cateIds;

    @Schema(description = "商品描述")
    private String content;

    @Schema(description = "收藏数量")
    private Integer collectCount;

    @Schema(description = "优惠券")
    private List<StoreCoupon> coupons;

    @Schema(description = "优惠券Ids")
    private List<Integer> couponIds;

    // 秒杀用到
    @Schema(description = "活动标题")
    private String title;

    @Schema(description = "简介")
    private String info;

    @Schema(description = "时间段ID")
    private Integer timeId;

    @Schema(description = "最多秒杀几个")
    private Integer num;

    @Schema(description = "开始时间")
    private Date startTime;

    @Schema(description = "结束时间")
    private Date stopTime;

    @Schema(description = "开始时间")
    private String startTimeStr;

    @Schema(description = "结束时间")
    private String stopTimeStr;

    @Schema(description = "秒杀状态 0=关闭 1=开启")
    private Integer status;

    @Schema(description = "商品id")
    private Integer productId;

    @Schema(description = "秒杀轮播图")
    private String images;

    @Schema(description = "限购数量 - 销量")
    private Integer quota;

    @Schema(description = "限购总数")
    private Integer quotaShow;

    @Schema(description = "砍价规则")
    private String rule;

    @Schema(description = "用户每次砍价的次数")
    private Integer bargainNum;

    @Schema(description = "帮助砍价好友人数")
    private Integer peopleNum;

    // 拼团部分
    @Schema(description = "推荐")
    private Boolean isHost;

    @Schema(description = "参团人数")
    private Integer people;

    @Schema(description = "拼团订单有效时间(小时)")
    private Integer effectiveTime;

    @Schema(description = "单次购买数量")
    private Integer onceNum;

    @Schema(description = "虚拟成团百分比")
    private Integer virtualRation;

    @Schema(description = "砍价商品最低价")
    private BigDecimal minPrice;

    @Schema(description = "砍价结束时间")
    private Long endTime;
}
