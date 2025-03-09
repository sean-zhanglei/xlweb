package com.nbug.mico.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 获取微信用户信息

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="获取微信用户信息")
public class WeChatAuthorizeLoginUserInfoVo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户的唯一标识")
    @TableField(value = "openId")
    private String openId;

    @Schema(description = "用户昵称")
    @TableField(value = "nickname")
    private String nickName;

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "用户个人资料填写的省份")
    private String province;

    @Schema(description = "普通用户个人资料填写的城市")
    private String city;

    @Schema(description = "国家，如中国为CN")
    private String country;

    @Schema(description = "用户头像")
    private String headimgurl;

    @Schema(description = "用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）")
    private String privilege;

    @Schema(description = "只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。")
    @TableField(value = "unionid")
    private String unionId;

}
