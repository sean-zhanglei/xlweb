package com.nbug.module.infra.websockets.message;

import lombok.Data;

/**
 * 示例：server -> client 同步消息
 *
 * @author NBUG
 */
@Data
public class WebSocketReceiveMessage {

    /**
     * 接收人的编号
     */
    private Long fromUserId;
    /**
     * 内容
     */
    private String text;

    /**
     * 是否单聊
     */
    private Boolean single;

}
