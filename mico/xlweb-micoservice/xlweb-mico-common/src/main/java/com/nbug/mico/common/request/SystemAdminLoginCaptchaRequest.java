package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * PC登录请求对象 行为验证码

 */
@Data
public class SystemAdminLoginCaptchaRequest {

    @Schema(required = true)
    String captchaVerification;

    @Schema(required = true)
    String token;

    @Schema(required = true)
    String secretKey;


}

