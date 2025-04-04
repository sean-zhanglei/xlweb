package com.nbug.module.infra.websockets.message;

import lombok.Data;

/**
 * 示例：client -> server 发送消息
 *
 * @author NBUG
 */
@Data
public class WebSocketSendMessage {

    /**
     * 发送给谁
     *
     * 如果为空，说明发送给所有人
     */
    private Long toUserId;
    /**
     * 内容
     */
    private String text;

}
