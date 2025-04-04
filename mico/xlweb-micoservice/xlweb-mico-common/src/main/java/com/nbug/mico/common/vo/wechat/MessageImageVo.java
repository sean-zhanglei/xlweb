package com.nbug.mico.common.vo.wechat;

import com.nbug.mico.common.constants.WeChatConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name="MessageMediaVo对象", description="微信消息 图片/语音 模板")
public class MessageImageVo extends BaseMessageVo {
    public MessageImageVo() {}
    public MessageImageVo(String toUserName, String fromUserName, MessageImageItemVo image) {
        super();
        ToUserName = toUserName;
        FromUserName = fromUserName;
        MsgType = WeChatConstants.WE_CHAT_MESSAGE_RESP_MESSAGE_TYPE_IMAGE;
        Image = image;
    }

    @Schema(name = "通过素材管理中的接口上传多媒体文件，得到的id。")
    private MessageImageItemVo Image;
}
