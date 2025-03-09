package com.nbug.mico.common.model.wechat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信二维码管理表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_wechat_qrcode")
@Schema(description="微信二维码管理表")
public class WechatQrcode implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "微信二维码ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "二维码类型")
    private String thirdType;

    @Schema(description = "用户id")
    private Integer thirdId;

    @Schema(description = "二维码参数")
    private String ticket;

    @Schema(description = "二维码有效时间")
    private Integer expireSeconds;

    @Schema(description = "状态")
    private Boolean status;

    @Schema(description = "微信访问url")
    private String url;

    @Schema(description = "微信二维码url")
    private String qrcodeUrl;

    @Schema(description = "被扫的次数")
    private Integer scan;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;


}
