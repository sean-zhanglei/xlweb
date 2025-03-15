package com.nbug.mico.common.request;

import com.nbug.mico.common.constants.RegularConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 换绑手机号请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="换绑手机号请求对象")
public class UserBindingPhoneUpdateRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "手机号", required = true)
    @Pattern(regexp = RegularConstants.PHONE_TWO, message = "手机号码格式错误")
    private String phone;

    @Schema(description = "验证码", required = true)
    @Pattern(regexp = RegularConstants.VALIDATE_CODE_NUM_SIX, message = "验证码格式错误，验证码必须为6位数字")
    private String captcha;
}
