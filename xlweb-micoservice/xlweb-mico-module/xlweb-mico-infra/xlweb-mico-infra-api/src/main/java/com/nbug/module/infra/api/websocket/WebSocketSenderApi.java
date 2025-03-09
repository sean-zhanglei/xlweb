package com.nbug.module.infra.api.websocket;

import com.nbug.mico.common.json.JsonUtils;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.api.websocket.dto.WebSocketSendReqDTO;
import com.nbug.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - WebSocket 发送器的") // 对 WebSocketMessageSender 进行封装，提供给其它模块使用
public interface WebSocketSenderApi {

    String PREFIX = ApiConstants.PREFIX + "/websocket";

    @PostMapping(PREFIX + "/send")
    @Operation(summary = "发送 WebSocket 消息")
    CommonResult<Boolean> send(@Valid @RequestBody WebSocketSendReqDTO message);

    /**
     * 发送消息给指定用户
     *
     * @param userType 用户类型
     * @param userId 用户编号
     * @param messageType 消息类型
     * @param messageContent 消息内容，JSON 格式
     */
    default void send(Integer userType, Long userId, String messageType, String messageContent) {
        WebSocketSendReqDTO webSocketSendReqDTO = new WebSocketSendReqDTO();
        webSocketSendReqDTO.setUserType(userType);
        webSocketSendReqDTO.setUserId(userId);
        webSocketSendReqDTO.setMessageType(messageType);
        webSocketSendReqDTO.setMessageContent(messageContent);
        send(webSocketSendReqDTO).checkError();
    }

    /**
     * 发送消息给指定用户类型
     *
     * @param userType 用户类型
     * @param messageType 消息类型
     * @param messageContent 消息内容，JSON 格式
     */
    default void send(Integer userType, String messageType, String messageContent) {
        WebSocketSendReqDTO webSocketSendReqDTO = new WebSocketSendReqDTO();
        webSocketSendReqDTO.setUserType(userType);
        webSocketSendReqDTO.setMessageType(messageType);
        webSocketSendReqDTO.setMessageContent(messageContent);
        send(webSocketSendReqDTO).checkError();
    }

    /**
     * 发送消息给指定 Session
     *
     * @param sessionId Session 编号
     * @param messageType 消息类型
     * @param messageContent 消息内容，JSON 格式
     */
    default void send(String sessionId, String messageType, String messageContent) {
        WebSocketSendReqDTO webSocketSendReqDTO = new WebSocketSendReqDTO();
        webSocketSendReqDTO.setSessionId(sessionId);
        webSocketSendReqDTO.setMessageType(messageType);
        webSocketSendReqDTO.setMessageContent(messageContent);
        send(webSocketSendReqDTO).checkError();
    }

    default void sendObject(Integer userType, Long userId, String messageType, Object messageContent) {
        send(userType, userId, messageType, JsonUtils.toJsonString(messageContent));
    }

    default void sendObject(Integer userType, String messageType, Object messageContent) {
        send(userType, messageType, JsonUtils.toJsonString(messageContent));
    }

    default void sendObject(String sessionId, String messageType, Object messageContent) {
        send(sessionId, messageType, JsonUtils.toJsonString(messageContent));
    }

}
