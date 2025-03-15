package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * PC登录请求对象

 */
@Data
public class SystemAdminLoginRequest {
    @Schema(description = "后台管理员账号", example = "userName")
    @NotEmpty(message = "账号 不能为空")
    @Length(max = 32, message = "账号长度不能超过32个字符")
    private String account;

    @Schema(description = "后台管理员密码", example = "userPassword")
    @NotEmpty(message = "密码 不能为空")
    @Length(min = 6, max = 30 ,message = "密码长度在6-30个字符")
    private String pwd;

    @Schema(description = "key", required = true)
    @NotEmpty(message = "验证码key 不能为空")
    private String key;

    @Schema(description = "code", required = true)
    @NotEmpty(message = "验证码 不能为空")
    private String code;
}
