package com.nbug.mico.common.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nbug.mico.common.constants.RegularConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 绑定手机号

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="修改密码")
public class PasswordRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = RegularConstants.PHONE_TWO, message = "手机号码格式错误")
    @JsonProperty(value = "account")
    private String phone;

    @Schema(description = "密码", required = true)
    @Pattern(regexp = RegularConstants.PASSWORD, message = "密码格式错误，密码必须以字母开头，长度在6~18之间，只能包含字符、数字和下划线")
    private String password;

    @Schema(description = "验证码", required = true)
    @Pattern(regexp = RegularConstants.VALIDATE_CODE_NUM_SIX, message = "验证码格式错误，验证码必须为6位数字")
    @JsonProperty(value = "captcha")
    private String validateCode;


}
