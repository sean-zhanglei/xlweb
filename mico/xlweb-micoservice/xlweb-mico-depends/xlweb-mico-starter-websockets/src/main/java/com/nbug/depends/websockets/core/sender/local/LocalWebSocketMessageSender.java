package com.nbug.depends.websockets.core.sender.local;

import com.nbug.depends.websockets.core.sender.AbstractWebSocketMessageSender;
import com.nbug.depends.websockets.core.sender.WebSocketMessageSender;
import com.nbug.depends.websockets.core.session.WebSocketSessionManager;

/**
 * 本地的 {@link WebSocketMessageSender} 实现类
 *
 * 注意：仅仅适合单机场景！！！
 *
 * @author NBUG
 */
public class LocalWebSocketMessageSender extends AbstractWebSocketMessageSender {

    public LocalWebSocketMessageSender(WebSocketSessionManager sessionManager) {
        super(sessionManager);
    }

}
