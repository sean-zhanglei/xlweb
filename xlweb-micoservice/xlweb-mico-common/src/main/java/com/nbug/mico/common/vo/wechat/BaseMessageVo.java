package com.nbug.module.infra.service.wechat.vo;

import com.nbug.mico.common.utils.date.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 微信消息基础模板

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name="BaseMessageVo对象", description="微信消息基础模板")
public class BaseMessageVo{
    @Schema(name = "开发者微信号")
    protected String ToUserName;

    @Schema(name = "发送方帐号（一个OpenID）")
    protected String FromUserName;

    @Schema(name = "消息创建时间 （整型）")
    protected Long CreateTime = DateUtil.getTime();

    @Schema(name = "消息类型，文本为text")
    protected String MsgType = "text";
}
