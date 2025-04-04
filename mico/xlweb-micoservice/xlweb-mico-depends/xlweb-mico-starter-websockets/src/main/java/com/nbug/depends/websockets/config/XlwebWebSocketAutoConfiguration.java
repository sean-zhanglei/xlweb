package com.nbug.depends.websockets.config;

import com.nbug.depends.mq.redis.config.XlwebRedisMQConsumerAutoConfiguration;
import com.nbug.depends.mq.redis.core.RedisMQTemplate;
import com.nbug.depends.websockets.core.handler.JsonWebSocketMessageHandler;
import com.nbug.depends.websockets.core.listener.WebSocketMessageListener;
import com.nbug.depends.websockets.core.security.LoginUserHandshakeInterceptor;
import com.nbug.depends.websockets.core.security.WebSocketAuthorizeRequestsCustomizer;
import com.nbug.depends.websockets.core.sender.kafka.KafkaWebSocketMessageConsumer;
import com.nbug.depends.websockets.core.sender.kafka.KafkaWebSocketMessageSender;
import com.nbug.depends.websockets.core.sender.local.LocalWebSocketMessageSender;
import com.nbug.depends.websockets.core.sender.rabbitmq.RabbitMQWebSocketMessageConsumer;
import com.nbug.depends.websockets.core.sender.rabbitmq.RabbitMQWebSocketMessageSender;
import com.nbug.depends.websockets.core.sender.redis.RedisWebSocketMessageConsumer;
import com.nbug.depends.websockets.core.sender.redis.RedisWebSocketMessageSender;
import com.nbug.depends.websockets.core.sender.rocketmq.RocketMQWebSocketMessageConsumer;
import com.nbug.depends.websockets.core.sender.rocketmq.RocketMQWebSocketMessageSender;
import com.nbug.depends.websockets.core.session.WebSocketSessionHandlerDecorator;
import com.nbug.depends.websockets.core.session.WebSocketSessionManager;
import com.nbug.depends.websockets.core.session.WebSocketSessionManagerImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Objects;

/**
 * WebSocket 自动配置
 *
 * @author xingyu4j
 */
@AutoConfiguration(before = XlwebRedisMQConsumerAutoConfiguration.class) // before XlwebRedisMQConsumerAutoConfiguration 的原因是，需要保证 RedisWebSocketMessageConsumer 先创建，才能创建 RedisMessageListenerContainer
@EnableWebSocket // 开启 websocket
@ConditionalOnProperty(prefix = "xlweb.websocket", value = "enable", matchIfMissing = true) // 允许使用 xlweb.websocket.enable=false 禁用 websocket
@EnableConfigurationProperties(WebSocketProperties.class)
@Slf4j
public class XlwebWebSocketAutoConfiguration {

    @Bean
    public WebSocketConfigurer webSocketConfigurer(HandshakeInterceptor[] handshakeInterceptors,
                                                   WebSocketHandler webSocketHandler,
                                                   WebSocketProperties webSocketProperties) {
        return registry -> registry
                // 添加 WebSocketHandler
                .addHandler(webSocketHandler, webSocketProperties.getPath())
                .addInterceptors(handshakeInterceptors)
                // 允许跨域，否则前端连接会直接断开
                .setAllowedOriginPatterns("*");
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new LoginUserHandshakeInterceptor();
    }

    @Bean
    public WebSocketHandler webSocketHandler(WebSocketSessionManager sessionManager,
                                             List<? extends WebSocketMessageListener<?>> messageListeners) {
        // 1. 创建 JsonWebSocketMessageHandler 对象，处理消息
        JsonWebSocketMessageHandler messageHandler = new JsonWebSocketMessageHandler(messageListeners);
        // 2. 创建 WebSocketSessionHandlerDecorator 对象，处理连接
        return new WebSocketSessionHandlerDecorator(messageHandler, sessionManager);
    }

    @Bean
    public WebSocketSessionManager webSocketSessionManager() {
        return new WebSocketSessionManagerImpl();
    }

    @Bean
    public WebSocketAuthorizeRequestsCustomizer webSocketAuthorizeRequestsCustomizer(WebSocketProperties webSocketProperties) {
        return new WebSocketAuthorizeRequestsCustomizer(webSocketProperties);
    }

    @Bean(name = "rabbitMQWebSocketMessageConsumerErrorHandler")
    public RabbitListenerErrorHandler rabbitListenerErrorHandler() {
        return (amqpMessage, message, exception) -> {
            try {
                // 空值检查
                if (message == null || amqpMessage == null) {
                    log.error("Consumer Received null message or AMQP message in RabbitMQ consumer.");
                    return null;
                }
                // 获取消息内容
                String messageContent = message.getPayload().toString();
                // 异常处理
                if (exception != null) {
                    log.error("Consumer Error processing RabbitMQ message: {}, 拒绝消息，不重新排队", messageContent, exception);
                    // 消费失败处理，重新消费或者直接丢弃
                }
                Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
                if (Objects.nonNull(channel))
                    channel.basicReject(amqpMessage.getMessageProperties().getDeliveryTag(), false); // 拒绝消息，不重新排队
                return false;
            } catch (Exception e) {
                log.error("Consumer Unexpected error occurred while processing RabbitMQ message.", e);
                return null;
            }
        };
    }

    @Bean(name = "rabbitJackson2JsonMessageConverter")
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    // ==================== Sender 相关 ====================

    @Configuration
    @ConditionalOnProperty(prefix = "xlweb.websocket", name = "sender-type", havingValue = "local")
    public class LocalWebSocketMessageSenderConfiguration {

        @Bean
        public LocalWebSocketMessageSender localWebSocketMessageSender(WebSocketSessionManager sessionManager) {
            return new LocalWebSocketMessageSender(sessionManager);
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "xlweb.websocket", name = "sender-type", havingValue = "redis")
    public class RedisWebSocketMessageSenderConfiguration {

        @Bean
        public RedisWebSocketMessageSender redisWebSocketMessageSender(WebSocketSessionManager sessionManager,
                                                                       RedisMQTemplate redisMQTemplate) {
            return new RedisWebSocketMessageSender(sessionManager, redisMQTemplate);
        }

        @Bean
        public RedisWebSocketMessageConsumer redisWebSocketMessageConsumer(
                RedisWebSocketMessageSender redisWebSocketMessageSender) {
            return new RedisWebSocketMessageConsumer(redisWebSocketMessageSender);
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "xlweb.websocket", name = "sender-type", havingValue = "rocketmq")
    public class RocketMQWebSocketMessageSenderConfiguration {

        @Bean
        public RocketMQWebSocketMessageSender rocketMQWebSocketMessageSender(
                WebSocketSessionManager sessionManager, RocketMQTemplate rocketMQTemplate,
                @Value("${xlweb.websocket.sender-rocketmq.topic}") String topic) {
            return new RocketMQWebSocketMessageSender(sessionManager, rocketMQTemplate, topic);
        }

        @Bean
        public RocketMQWebSocketMessageConsumer rocketMQWebSocketMessageConsumer(
                RocketMQWebSocketMessageSender rocketMQWebSocketMessageSender) {
            return new RocketMQWebSocketMessageConsumer(rocketMQWebSocketMessageSender);
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "xlweb.websocket", name = "sender-type", havingValue = "rabbitmq")
    public class RabbitMQWebSocketMessageSenderConfiguration {

        @Bean
        public RabbitMQWebSocketMessageSender rabbitMQWebSocketMessageSender(
                WebSocketSessionManager sessionManager, RabbitTemplate rabbitTemplate,
                TopicExchange websocketTopicExchange) {
            return new RabbitMQWebSocketMessageSender(sessionManager, rabbitTemplate, websocketTopicExchange);
        }

        @Bean
        public RabbitMQWebSocketMessageConsumer rabbitMQWebSocketMessageConsumer(
                RabbitMQWebSocketMessageSender rabbitMQWebSocketMessageSender) {
            return new RabbitMQWebSocketMessageConsumer(rabbitMQWebSocketMessageSender);
        }

        /**
         * 创建 Dead Topic Exchange
         */
        @Bean
        public TopicExchange websocketDeadTopicExchange(@Value("${xlweb.websocket.sender-rabbitmq.dead-letter-exchange}") String exchange) {
            return new TopicExchange(exchange,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        /**
         * 创建 Topic Exchange
         */
        @Bean
        public TopicExchange websocketTopicExchange(@Value("${xlweb.websocket.sender-rabbitmq.exchange}") String exchange) {
            return new TopicExchange(exchange,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "xlweb.websocket", name = "sender-type", havingValue = "kafka")
    public class KafkaWebSocketMessageSenderConfiguration {

        @Bean
        public KafkaWebSocketMessageSender kafkaWebSocketMessageSender(
                WebSocketSessionManager sessionManager, KafkaTemplate<Object, Object> kafkaTemplate,
                @Value("${xlweb.websocket.sender-kafka.topic}") String topic) {
            return new KafkaWebSocketMessageSender(sessionManager, kafkaTemplate, topic);
        }

        @Bean
        public KafkaWebSocketMessageConsumer kafkaWebSocketMessageConsumer(
                KafkaWebSocketMessageSender kafkaWebSocketMessageSender) {
            return new KafkaWebSocketMessageConsumer(kafkaWebSocketMessageSender);
        }

    }

}