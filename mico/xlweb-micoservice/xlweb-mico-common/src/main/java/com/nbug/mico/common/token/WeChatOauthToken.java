package com.nbug.mico.common.token;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 微信用户授权返回数据

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="微信开放平台获取accessToken对象")
public class WeChatOauthToken implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "accessToken接口调用凭证")
    @TableField(value = "access_token")
    private String accessToken;

    @Schema(description = "access_token 接口调用凭证超时时间，单位（秒）")
    @TableField(value = "expires_in")
    private String expiresIn;

    @Schema(description = "用户刷新access_token")
    @TableField(value = "refresh_token")
    private String refreshToken;

    @Schema(description = "用户OpenId")
    @TableField(value = "openid")
    private String openId;

    @Schema(description = "用户授权的作用域，使用逗号（,）分隔")
    private String scope;

}
