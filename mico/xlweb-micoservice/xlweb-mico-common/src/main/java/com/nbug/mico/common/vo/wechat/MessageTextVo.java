package com.nbug.module.infra.service.wechat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 微信消息文本模板

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name="MessageTextVo对象", description="微信消息文本模板")
public class MessageTextVo extends BaseMessageVo {
    public MessageTextVo() {}
    public MessageTextVo(String toUserName, String fromUserName, String content) {
        super();
        ToUserName = toUserName;
        FromUserName = fromUserName;
        Content = content;
    }

    @Schema(name = "文本消息内容")
    private String Content;
}
