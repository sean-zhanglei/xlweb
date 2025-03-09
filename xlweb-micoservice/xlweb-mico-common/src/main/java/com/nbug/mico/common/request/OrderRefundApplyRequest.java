package com.nbug.mico.common.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 添加购物车参数Request对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="订单申请退款")
public class OrderRefundApplyRequest {
    private static final long serialVersionUID=1L;

    @Schema(description = "退款原因", required = true)
    @NotNull(message = "退款原因必须填写")
    @Length(max = 255, message = "退款原因不能超过255个字符")
    private String text;

    @Schema(description = "订单id", required = true)
    private Integer id;

    @Schema(description = "退款凭证图片(多个图片请用,(英文逗号)隔开)")
    @JsonProperty("refund_reason_wap_img")
    private String reasonImage;

    @Schema(description = "备注说明")
    @JsonProperty("refund_reason_wap_explain")
    private String explain;

    @Schema(description = "待退款订单")
    @NotNull(message = "待退款订单 不能为空")
    private String  uni;
}
