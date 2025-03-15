package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * ios登录请求体
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="ios登录请求体")
public class IosLoginRequest {

    @Schema(description = "iosToken", required = true)
//    @NotBlank(message = "identityToken不能为空")
    private String identityToken;

    @Schema(description = "App服务商唯一用户标识", required = true)
    @NotBlank(message = "openId不能为空")
    private String openId;

    @Schema(description = "Ios用户电子邮箱")
//    @NotBlank(message = "email不能为空")
    private String email;
}
