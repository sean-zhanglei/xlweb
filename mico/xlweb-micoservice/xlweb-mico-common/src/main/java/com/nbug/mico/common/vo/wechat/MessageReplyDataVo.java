package com.nbug.module.infra.service.wechat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 数据存储回复消息内容对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name="MessageReplyDataVo对象", description="数据存储回复消息内容对象")
public class MessageReplyDataVo{

    @Schema(name = "文本消息内容")
    private String Content;

    @Schema(name = "图片/音频链接")
    private String src;

    @Schema(name = "图片/音频媒体ID")
    private String mediaId;

    @Schema(name = "文本消息内容")
    private Integer articleId;
}
