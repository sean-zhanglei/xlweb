package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * App微信注册/登录请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="App微信注册/登录请求对象")
public class RegisterAppWxRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户openId")
    private String openId;

    @Schema(description = "用户unionId")
    private String unionId;

    @Schema(description = "用户昵称", required = true)
    private String nickName;

    @Schema(description = "性别:0未知，1-男，2-女", required = true)
    private String gender;

    @Schema(description = "用户个人资料填写的省份")
    private String province;

    @Schema(description = "普通用户个人资料填写的城市")
    private String city;

    @Schema(description = "国家，如中国为CN")
    private String country;

    @Schema(description = "微信App用户头像", required = true)
    private String avatarUrl;

    @Schema(description = "微信App类型：iosWx-苹果微信，androidWx-安卓微信", required = true)
    private String type;

}
