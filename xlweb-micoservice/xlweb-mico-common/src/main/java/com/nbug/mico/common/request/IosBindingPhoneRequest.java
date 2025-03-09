package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 微信绑定手机号请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="Ios绑定手机号请求对象")
public class IosBindingPhoneRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "手机号", required = true)
    private String phone;

    @Schema(description = "验证码", required = true)
    private String captcha;
}
