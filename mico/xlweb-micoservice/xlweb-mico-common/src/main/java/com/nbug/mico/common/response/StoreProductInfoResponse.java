package com.nbug.mico.common.response;

import com.nbug.mico.common.model.product.StoreProductAttr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 商品详情响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="商品详情响应对象")
public class StoreProductInfoResponse implements Serializable {

    private static final long serialVersionUID = 9215241889318610262L;

    @Schema(description = "商品id")
    private Integer id;

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

    @Schema(description = "分类id")
    private String cateId;

    @Schema(description = "分类中文")
    private String cateStr;

    @Schema(description = "单位名")
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

    @Schema(description = "获得积分")
    private Integer giveIntegral;

    @Schema(description = "是否优品推荐")
    private Boolean isGood;

    @Schema(description = "是否单独分佣")
    private Boolean isSub;

    @Schema(description = "虚拟销量")
    private Integer ficti;

    @Schema(description = "运费模板ID")
    private Integer tempId;

    @Schema(description = "规格 0单 1多")
    private Boolean specType;

    @Schema(description = "活动显示排序 0=默认，1=秒杀，2=砍价，3=拼团")
    private List<String> activity;

    @Schema(description = "商品属性")
    private List<StoreProductAttr> attr;

    @Schema(description = "商品属性详情")
    private List<AttrValueResponse> attrValue;

    @Schema(description = "商品描述")
    private String content;

    @Schema(description = "产品ID")
    private Integer productId;

//    @Schema(description = "优惠券")
//    private List<StoreCoupon> coupons;

    @Schema(description = "优惠券Ids")
    private List<Integer> couponIds;

    // 以下为活动商品部分

    @Schema(description = "状态 0=关闭 1=开启|营销商品用")
    private Integer status;

    @Schema(description = "运费模板ID|秒杀商品专用")
    private Integer timeId;

    @Schema(description = "秒杀开启时间|秒杀专用")
    private String startTimeStr;

    @Schema(description = "秒杀结束时间|秒杀专用")
    private String stopTimeStr;

    @Schema(description = "当天参与秒杀次数|秒杀专用")
    private Integer num;

    @Schema(description = "砍价开启时间|砍价专用")
    private Long startTime;

    @Schema(description = "砍价结束时间|砍价专用")
    private Long stopTime;

    @Schema(description = "砍价活动名称|砍价专用")
    private String title;

    @Schema(description = "帮砍次数|砍价专用")
    private Integer bargainNum;

    @Schema(description = "帮助砍价好友人数|砍价专用")
    private Integer peopleNum;

    @Schema(description = "拼团订单有效时间(小时)|拼团专用")
    private Integer effectiveTime;

    @Schema(description = "每个订单可购买数量|拼团专用")
    private Integer onceNum;

    @Schema(description = "虚拟成团百分比|拼团专用")
    private Integer virtualRation;

    @Schema(description = "参团人数|拼团专用")
    private Integer people;

    @Schema(description = "商品状态|拼团专用")
    private Boolean isShow;

    @Schema(description = "简介|拼团专用")
    private String info;

    @Schema(description = "展示图")
    private String flatPattern;
}
