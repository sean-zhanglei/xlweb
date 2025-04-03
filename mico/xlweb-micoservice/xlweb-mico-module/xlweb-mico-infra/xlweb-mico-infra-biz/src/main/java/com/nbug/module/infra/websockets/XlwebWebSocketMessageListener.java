package com.nbug.module.infra.websockets;

import com.nbug.depends.websockets.core.listener.WebSocketMessageListener;
import com.nbug.depends.websockets.core.sender.WebSocketMessageSender;
import com.nbug.depends.websockets.core.util.WebSocketFrameworkUtils;
import com.nbug.mico.common.enums.UserTypeEnum;
import com.nbug.module.infra.websockets.message.WebSocketReceiveMessage;
import com.nbug.module.infra.websockets.message.WebSocketSendMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

/**
 * WebSocket 示例：单发消息
 *
 * @author NBUG
 */
@Component
public class XlwebWebSocketMessageListener implements WebSocketMessageListener<WebSocketSendMessage> {

    @Resource
    private WebSocketMessageSender webSocketMessageSender;

    @Override
    public void onMessage(WebSocketSession session, WebSocketSendMessage message) {
        Long fromUserId = WebSocketFrameworkUtils.getLoginUserId(session);
        // 情况一：单发
        if (message.getToUserId() != null) {
            WebSocketReceiveMessage toMessage = new WebSocketReceiveMessage().setFromUserId(fromUserId)
                    .setText(message.getText()).setSingle(true);
            webSocketMessageSender.sendObject(UserTypeEnum.ADMIN.getValue(), message.getToUserId(), // 给指定用户
                    "demo-message-receive", toMessage);
            return;
        }
        // 情况二：群发
        WebSocketReceiveMessage toMessage = new WebSocketReceiveMessage().setFromUserId(fromUserId)
                .setText(message.getText()).setSingle(false);
        webSocketMessageSender.sendObject(UserTypeEnum.ADMIN.getValue(), // 给所有用户
                "demo-message-receive", toMessage);
    }

    @Override
    public String getType() {
        return "demo-message-send";
    }

}
