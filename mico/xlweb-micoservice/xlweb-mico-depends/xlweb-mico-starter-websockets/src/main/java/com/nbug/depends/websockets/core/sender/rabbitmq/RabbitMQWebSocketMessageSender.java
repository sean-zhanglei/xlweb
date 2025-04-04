package com.nbug.depends.websockets.core.sender.rabbitmq;

import com.nbug.depends.websockets.core.sender.AbstractWebSocketMessageSender;
import com.nbug.depends.websockets.core.sender.WebSocketMessageSender;
import com.nbug.depends.websockets.core.session.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

/**
 * 基于 RabbitMQ 的 {@link WebSocketMessageSender} 实现类
 *
 * @author NBUG
 */
@Slf4j
public class RabbitMQWebSocketMessageSender extends AbstractWebSocketMessageSender {

    private final RabbitTemplate rabbitTemplate;

    private final TopicExchange topicExchange;

    public RabbitMQWebSocketMessageSender(WebSocketSessionManager sessionManager,
                                          RabbitTemplate rabbitTemplate,
                                          TopicExchange topicExchange) {
        super(sessionManager);
        this.rabbitTemplate = rabbitTemplate;
        this.topicExchange = topicExchange;
    }

    @Override
    public void send(Integer userType, Long userId, String messageType, String messageContent) {
        sendRabbitMQMessage(null, userId, userType, messageType, messageContent);
    }

    @Override
    public void send(Integer userType, String messageType, String messageContent) {
        sendRabbitMQMessage(null, null, userType, messageType, messageContent);
    }

    @Override
    public void send(String sessionId, String messageType, String messageContent) {
        sendRabbitMQMessage(sessionId, null, null, messageType, messageContent);
    }

    /**
     * 通过 RabbitMQ 广播消息
     *
     * @param sessionId Session 编号
     * @param userId 用户编号
     * @param userType 用户类型
     * @param messageType 消息类型
     * @param messageContent 消息内容
     */
    private void sendRabbitMQMessage(String sessionId, Long userId, Integer userType,
                                     String messageType, String messageContent) {
        RabbitMQWebSocketMessage mqMessage = new RabbitMQWebSocketMessage()
                .setSessionId(sessionId).setUserId(userId).setUserType(userType)
                .setMessageType(messageType).setMessageContent(messageContent);
        // 发送消息，但不自动确认
        MessagePostProcessor messagePostProcessor = message -> {
            // 这里可以自定义消息属性，例如设置消息的持久化等
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setExpiration("100000"); // 设置消息过期时间10s，单位毫秒
            return message;
        };
        // rabbitTemplate.setMandatory(true); // 开启消息确认，已经通过配置参数实现
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (! ack) {
                log.error("[sendRabbitMQMessage][发送消息({}) 进入交换机失败，原因：{}，消息：{}]",
                        correlationData != null ? correlationData.getId() : "null", cause, mqMessage);
                // 失败重试3次 + 持久化DB/Redis + 通过定时任务重新发送
            } else {
                log.info("[sendRabbitMQMessage][发送消息({}) 已经成功进入交换机]", mqMessage);
            }
        });
        rabbitTemplate.setReturnsCallback((returnedMessage) -> {
            String message = String.format("消息体: %s, 回复码: %d, 回复文本: %s, 交换机: %s, 路由键: %s",
                    returnedMessage.getMessage(), returnedMessage.getReplyCode(),
                    returnedMessage.getReplyText(), returnedMessage.getExchange(),
                    returnedMessage.getRoutingKey());
            log.error("[sendRabbitMQMessage][发送消息进入队列失败] {}", message);
            // 失败重试3次 + 持久化DB/Redis + 通过定时任务重新发送
        });
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend(topicExchange.getName(), null, mqMessage, messagePostProcessor);
    }

}
