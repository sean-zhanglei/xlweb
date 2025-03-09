package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 优惠卷领取搜索

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_coupon_user")
@Schema( description="优惠券领取搜索")
public class StoreCouponUserSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "优惠券所属用户")
    private Integer uid;

    @Schema(description = "优惠券id")
    private Integer couponId;

    @Schema(description = "优惠券名称")
    private String name;

    @Schema(description = "状态（0：未使用，1：已使用, 2:已过期）")
    private Integer status;

    @Schema(description = "最低消费多少金额可用优惠券")
    private BigDecimal minPrice;


}
