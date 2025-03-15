package com.nbug.mico.common.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户提现表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户提现")
public class UserExtractRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "姓名")
    @NotBlank(message = "提现用户名称必须填写")
    @Length(max = 64, message = "提现用户名称不能超过64个字符")
    @JsonProperty(value = "name")
    private String realName;

    @Schema(description = "提现方式| alipay=支付宝,bank=银行卡,weixin=微信", allowableValues = "range[alipay,weixin,bank]")
    @NotBlank(message = "请选择提现方式， 支付宝|微信|银行卡")
    private String extractType;

    @Schema(description = "银行卡")
    @JsonProperty(value = "cardum")
    private String bankCode;

    @Schema(description = "提现银行名称")
    private String bankName;

    @Schema(description = "支付宝账号")
    private String alipayCode;

    @Schema(description = "提现金额")
    @JsonProperty(value = "money")
    @NotNull(message = "请输入提现金额")
    @DecimalMin(value = "0.1", message = "提现金额不能小于0.1")
    private BigDecimal extractPrice;

    @Schema(description = "微信号")
    private String wechat;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "微信收款码")
    private String qrcodeUrl;
}
