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
 * <p>
 * 微信回调表
 * </p>
 *
 * @author HZW
 * @since 2021-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_wechat_callback")
@Schema(description="微信回调表")
public class WechatCallback implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "商家小程序名称")
    private String toUserName;

    @Schema(description = "微信团队的 OpenID(固定值)")
    private String fromUserName;

    @Schema(description = "事件时间,Unix时间戳")
    private Long createTime;

    @Schema(description = "消息类型")
    private String msgType;

    @Schema(description = "事件类型")
    private String event;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "创建时间")
    private Date addTime;


}
