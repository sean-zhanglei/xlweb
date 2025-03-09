package com.nbug.mico.common.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户地址表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="三方用户注册对象")
public class RegisterThirdUserRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户昵称", required = true)
    private String nickName;

    @Schema(description = "性别", required = true)
    private String sex;

    @Schema(description = "用户个人资料填写的省份")
    private String province;

    @Schema(description = "普通用户个人资料填写的城市")
    private String city;

    @Schema(description = "国家，如中国为CN")
    private String country;

    @Schema(description = "微信小程序用户头像", required = true)
    private String avatar;

    @Schema(description = "推广人id")
    @JsonProperty(value = "spread_spid", defaultValue = "0")
    private Integer spreadPid;

    @Schema(description = "微信公众号用户头像", required = true)
    private String headimgurl;

    @Schema(description = "用户类型:wechat-公众号，routine-小程序，h5-H5,iosWx-苹果微信，androidWx-安卓微信")
    private String type;

    @Schema(description = "用户openId")
    private String openId;
}
