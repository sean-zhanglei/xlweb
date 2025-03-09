package com.nbug.mico.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 微信小程序用户授权返回数据

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="微信小程序用户授权返回数据")
public class WeChatMiniAuthorizeVo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "会话密钥")
    @TableField(value = "session_key")
    private String sessionKey;

    @Schema(description = "用户唯一标识")
    @TableField(value = "openid")
    private String openId;

    @Schema(description = "用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回")
    @TableField(value = "unionid")
    private String unionId;

    @Schema(description = "错误码")
    @TableField(value = "errcode")
    private String errCode;

    @Schema(description = "错误信息")
    @TableField(value = "errmsg")
    private String errMsg;
}
