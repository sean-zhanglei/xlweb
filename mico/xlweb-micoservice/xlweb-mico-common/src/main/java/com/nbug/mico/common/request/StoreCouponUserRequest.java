package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 优惠券领取

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_coupon_user")
@Schema( description="优惠券领取")
public class StoreCouponUserRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "优惠券发布id")
    @NotNull(message = "优惠券id不能为空")
    private Integer couponId;

    @Schema(description = "领取人id, 多个id逗号分割")
    @NotBlank(message = "领取人不能为空")
    private String uid;
}
