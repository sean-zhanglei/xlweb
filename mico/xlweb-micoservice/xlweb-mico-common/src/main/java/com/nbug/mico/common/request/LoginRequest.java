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
 * 移动端手机密码登录请求对象
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="移动端手机密码登录请求对象")
public class LoginRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "手机号", required = true, example = "18888888")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = RegularConstants.PHONE_TWO, message = "手机号码格式错误")
    @JsonProperty(value = "account")
    private String phone;

    @Schema(description = "密码", required = true, example = "1~[6,18]")
//    @Pattern(regexp = RegularConstants.PASSWORD, message = "密码格式错误，密码必须以字母开头，长度在6~18之间，只能包含字符、数字和下划线")
    private String password;

    @Schema(description = "推广人id")
    @JsonProperty(value = "spread_spid")
    private Integer spreadPid = 0;
}
