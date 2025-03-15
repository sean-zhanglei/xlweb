package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 预下单Vo对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="预下单Vo对象")
public class OrderInfoVo {

    @Schema(description = "运费金额")
    private BigDecimal freightFee;

    @Schema(description = "优惠券编号（选择优惠券时有值")
    private Integer userCouponId;

    @Schema(description = "优惠金额")
    private BigDecimal couponFee;

    @Schema(description = "商品总计金额")
    private BigDecimal proTotalFee;

    @Schema(description = "订单商品数量")
    private Integer orderProNum;

    @Schema(description = "实际支付金额")
    private BigDecimal payFee;

    @Schema(description = "地址id")
    private Integer addressId;

    @Schema(description = "收货人姓名(前端用)")
    private String realName;

    @Schema(description = "收货人电话(前端用)")
    private String phone;

    @Schema(description = "收货人所在省(前端用)")
    private String province;

    @Schema(description = "收货人所在市(前端用)")
    private String city;

    @Schema(description = "收货人所在区(前端用)")
    private String district;

    @Schema(description = "收货人详细地址(前端用)")
    private String detail;

    @Schema(description = "用户剩余积分")
    private Integer userIntegral;

    @Schema(description = "用户可用余额")
    private BigDecimal userBalance;

    @Schema(description = "订单备注")
    private String remark;

    @Schema(description = "订单详情数组")
    private List<OrderInfoDetailVo> orderDetailList;

    @Schema(description = "秒杀商品Id")
    private Integer seckillId = 0;

    @Schema(description = "砍价商品Id")
    private Integer bargainId = 0;

    @Schema(description = "用户砍价活动id")
    private Integer bargainUserId;

    @Schema(description = "拼团商品Id")
    private Integer combinationId = 0;

    @Schema(description = "拼团团长Id")
    private Integer pinkId = 0;

    @Schema(description = "购物车编号列表")
    private List<Long> cartIdList;

    @Schema(description = "是否视频号订单")
    private Boolean isVideo = false;
}
