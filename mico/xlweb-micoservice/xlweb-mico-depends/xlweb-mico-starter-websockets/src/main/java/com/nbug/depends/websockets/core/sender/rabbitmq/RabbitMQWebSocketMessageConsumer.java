package com.nbug.depends.websockets.core.sender.rabbitmq;

import com.nbug.mico.common.utils.redis.RedisUtil;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

/**
 * {@link RabbitMQWebSocketMessage} 广播消息的消费者，真正把消息发送出去
 *
 * @author NBUG
 */
@RequiredArgsConstructor
@Slf4j
public class RabbitMQWebSocketMessageConsumer {

    private static final int RETRY_COUNT = 3; // 手动重试次数
    private static final long RETRY_INTERVAL = 2; // 手动重试间隔时间

    int autoRetryCount = 0;

    @Autowired
    RedisUtil redisUtil;

    private final RabbitMQWebSocketMessageSender rabbitMQWebSocketMessageSender;

    // 死信队列消费
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            // 在 Queue 的名字上，使用 UUID 生成其后缀。这样，启动的 Consumer 的 Queue 不同，以达到广播消费的目的
                            name = "${xlweb.websocket.sender-rabbitmq.dead-letter-queue}",
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-queue-mode", value = "lazy") // 惰性队列，直接刷盘，消费者下线、宕机、由于维护而关闭等原因致使长时间内不能消费消息而造成堆积
                            }
                    ),
                    exchange = @Exchange(
                            name = "${xlweb.websocket.sender-rabbitmq.dead-letter-exchange}",
                            type = ExchangeTypes.TOPIC,
                            declare = "false",
                            durable = "true"
                    ),
                    key = "${xlweb.websocket.sender-rabbitmq.dead-letter-routing-key}"
            ),
            errorHandler = "rabbitMQWebSocketMessageConsumerErrorHandler",
            messageConverter = "rabbitJackson2JsonMessageConverter"
    )
    @RabbitHandler
    public void onDeadMessage(RabbitMQWebSocketMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws InterruptedException, IOException {

        try {
            log.debug("死信队列消费消息：" + message);

            int n = 1/0;
            // 手动Ack
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("死信队列消费失败：" + message, e);
            // 记录死信队列消费失败
            // 1.记录失败信息
            redisUtil.lPush("WEBSOCKET::RABBITMQ_DEAD_MESSAGE", message);
            // 2.Nack 丢掉消息
            channel.basicNack(deliveryTag, false,false);
        }

    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            // 在 Queue 的名字上，使用 UUID 生成其后缀。这样，启动的 Consumer 的 Queue 不同，以达到广播消费的目的
                            name = "${xlweb.websocket.sender-rabbitmq.queue}" + "-" + "#{T(java.util.UUID).randomUUID()}",
                            durable = "true",
                            // Consumer 关闭时，该队列就可以被自动删除了
                            autoDelete = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "${xlweb.websocket.sender-rabbitmq.dead-letter-exchange}"), // 死信队列
                                    @Argument(name = "x-dead-letter-routing-key", value = "${xlweb.websocket.sender-rabbitmq.dead-letter-routing-key}") // 死信队列key
                            }
                    ),
                    exchange = @Exchange(
                            name = "${xlweb.websocket.sender-rabbitmq.exchange}",
                            type = ExchangeTypes.TOPIC,
                            declare = "false",
                            durable = "true"
                    ),
                    key = {"info", "error", "warning"}
            ),
            errorHandler = "rabbitMQWebSocketMessageConsumerErrorHandler",
            messageConverter = "rabbitJackson2JsonMessageConverter"
    )
    @RabbitHandler
    public void onMessage(RabbitMQWebSocketMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws InterruptedException, IOException {
        //1.acknowledge-mode=manual
        // 重试次数
        int retryCount = 0;
        boolean isSuccess = false;
        while (! isSuccess && retryCount < RETRY_COUNT) {
            retryCount ++;
            try {
                rabbitMQWebSocketMessageSender.send(message.getSessionId(),
                        message.getUserType(), message.getUserId(),
                        message.getMessageType(), message.getMessageContent());
                isSuccess = true;
            } catch (Exception e) {
                log.error("第" + retryCount + "次重试失败，消息：" + message + (retryCount == RETRY_COUNT ? "，重试失败，消息进入死信队列" :  "," + RETRY_INTERVAL + "秒后重试"));
                Thread.sleep(RETRY_INTERVAL * 1000);
            }
        }
        if(isSuccess) {
            // 手动确认消息
            channel.basicAck(deliveryTag, false);
        } else {
            channel.basicNack(deliveryTag, false, true);
            log.error("[onMessage][消费消息失败，进入死信队列，消息：{}]", message);
        }

        // 2.抛出异常，触发自动重试acknowledge-mode=auto，重试多次依旧失败进入死信队列
        /*try {
            autoRetryCount ++;
            rabbitMQWebSocketMessageSender.send(message.getSessionId(),
                    message.getUserType(), message.getUserId(),
                    message.getMessageType(), message.getMessageContent());
        } catch (Exception e) {
            log.error("第" + autoRetryCount + "次自动重试失败，消息：" + message);
            // 2.抛出异常，触发自动重试acknowledge-mode=auto，重试多次依旧失败进入死信队列
            throw new XlwebException("消费消息失败");
        }*/

        // 3.acknowledge-mode=manual,可以选择重新排队消息或拒绝消息
        /*try {
            rabbitMQWebSocketMessageSender.send(message.getSessionId(),
                    message.getUserType(), message.getUserId(),
                    message.getMessageType(), message.getMessageContent());
        } catch (Exception e) {
            log.error("第" + autoRetryCount + "次自动重试失败，消息：" + message);
            // channel.basicNack(deliveryTag, false, true); // 重新排队消息
            channel.basicReject(deliveryTag, false); // 拒绝消息，不重新排队
        }*/
    }

}
