package com.nbug.depends.websockets.core.sender.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * {@link KafkaWebSocketMessage} 广播消息的消费者，真正把消息发送出去
 *
 * @author NBUG
 */
@RequiredArgsConstructor
public class KafkaWebSocketMessageConsumer {

    private final KafkaWebSocketMessageSender kafkaWebSocketMessageSender;

    @KafkaListener(
            topics = "${xlweb.websocket.sender-kafka.topic}",
            // 在 Group 上，使用 UUID 生成其后缀。这样，启动的 Consumer 的 Group 不同，以达到广播消费的目的
            groupId = "${xlweb.websocket.sender-kafka.consumer-group}" + "-" + "#{T(java.util.UUID).randomUUID()}")
    public void onMessage(KafkaWebSocketMessage message) {
        kafkaWebSocketMessageSender.send(message.getSessionId(),
                message.getUserType(), message.getUserId(),
                message.getMessageType(), message.getMessageContent());
    }

}
