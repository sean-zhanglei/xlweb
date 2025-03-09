package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Login Response
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户登录返回数据")
public class LoginResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户登录密钥")
    private String token;

    @Schema(description = "状态:login-登录，register-注册,start-注册起始页")
    private String type;

    @Schema(description = "注册key")
    private String key;

    @Schema(description = "登录用户Uid")
    private Integer uid;

    @Schema(description = "登录用户昵称")
    private String nikeName;

    @Schema(description = "登录用户手机号")
    private String phone;
}
