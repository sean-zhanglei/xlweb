package com.nbug.mico.common.request;

import com.nbug.mico.common.annotation.StringContains;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 套餐购买请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description = "套餐购买请求对象")
public class MealCodeRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "套餐ID", required = true)
    @NotNull(message = "套餐ID不能为空")
    private Integer mealId;

    @Schema(description = "套餐金额", required = true)
    @NotNull(message = "套餐金额不能为空")
    private BigDecimal price;

    @Schema(description = "套餐量", required = true)
    @NotNull(message = "套餐量不能为空")
    private Integer num;

    @Schema(description = "套餐类型:sms,短信;copy,产品复制;expr_query,物流查询;expr_dump,电子面单", required = true)
    @NotBlank(message = "套餐类型不能为空")
    @StringContains(limitValues = {"sms","copy","expr_query","expr_dump"}, message = "未知的套餐类型")
    private String type;

    @Schema(description = "支付类型 weixin：微信支付/alipay：支付宝支付", required = true)
    @NotBlank(message = "支付类型不能为空")
    @StringContains(limitValues = {"weixin","alipay"}, message = "未知的支付类型")
    private String payType;

}
