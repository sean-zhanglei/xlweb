package com.nbug.mico.common.response;

import com.nbug.mico.common.model.bargain.StoreBargainUserHelp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 砍价用户详情响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="砍价用户详情响应对象")
public class BargainUserInfoResponse implements Serializable {

    private static final long serialVersionUID = 4177599369617161973L;

    @Schema(description = "当前用户砍价状态：1-可以参与砍价,2-参与次数已满，3-砍价中,4-已完成，5-可以帮砍，6-已帮砍,7-帮砍次数已满，8-已生成订单未支付，9-已支付，10-未支付，已取消")
    private Integer bargainStatus;

    @Schema(description = "已砍金额")
    private BigDecimal alreadyPrice;

    @Schema(description = "剩余金额")
    private BigDecimal surplusPrice;

    @Schema(description = "砍价百分比")
    private Integer bargainPercent;

//    @Schema(description = "砍价活动图片")
//    private String image;
//
//    @Schema(description = "单位名称")
//    private String unitName;
//
//    @Schema(description = "销量")
//    private Integer sales;
//
//    @Schema(description = "砍价开启时间")
//    private Long startTime;
//
//    @Schema(description = "砍价结束时间")
//    private Long stopTime;
//
//    @Schema(description = "砍价金额")
//    private BigDecimal price;
//
//    @Schema(description = "砍价商品最低价")
//    private BigDecimal minPrice;

//    @Schema(description = "购买数量限制")
//    // 单个活动每个用户发起砍价次数限制
//    private Integer num;

//    @Schema(description = "砍价活动简介")
//    private String info;

//    @Schema(description = "砍价规则")
//    private String rule;

//    @Schema(description = "限购总数")
//    private Integer quota;
//
//    @Schema(description = "限量总数显示")
//    private Integer quotaShow;
//
//    @Schema(description = "商品规格sku")
//    private String sku;
//
//    @Schema(description = "商品规格属性id")
//    private Integer attrValueId;

//    @Schema(description = "商品详情")
//    private String content;

    @Schema(description = "用户帮砍列表")
    private List<StoreBargainUserHelp> userHelpList;

    @Schema(description = "用户砍价活动id")
    private Integer storeBargainUserId;

    @Schema(description = "用户砍价活动昵称")
    private String storeBargainUserName;

    @Schema(description = "用户砍价活动头像")
    private String storeBargainUserAvatar;
}
