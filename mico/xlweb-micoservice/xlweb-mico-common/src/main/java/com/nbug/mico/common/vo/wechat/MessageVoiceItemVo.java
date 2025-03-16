package com.nbug.mico.common.vo.wechat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name="MessageVoiceItemVo对象", description="微信消息 图片/语音 模板")
public class MessageVoiceItemVo{
    public MessageVoiceItemVo() {}
    public MessageVoiceItemVo(String mediaId) {
        MediaId = mediaId;
    }

    @Schema(name = "通过素材管理中的接口上传多媒体文件，得到的id。")
    private String MediaId;
}
