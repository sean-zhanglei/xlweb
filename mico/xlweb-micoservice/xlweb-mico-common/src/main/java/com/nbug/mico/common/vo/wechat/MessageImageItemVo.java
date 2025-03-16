package com.nbug.module.infra.service.wechat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name="MessageImageItemVo对象", description="微信消息 图片/语音 模板")
public class MessageImageItemVo{
    public MessageImageItemVo() {}
    public MessageImageItemVo(String mediaId) {
        MediaId = mediaId;
    }

    @Schema(name = "通过素材管理中的接口上传多媒体文件，得到的id。")
    private String MediaId;
}
