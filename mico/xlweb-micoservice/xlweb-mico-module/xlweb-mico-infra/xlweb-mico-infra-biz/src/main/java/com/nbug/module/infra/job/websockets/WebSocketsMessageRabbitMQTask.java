package com.nbug.module.infra.job.websockets;

import com.nbug.depends.tenant.core.aop.TenantIgnore;
import com.nbug.depends.websockets.core.sender.rabbitmq.RabbitMQWebSocketMessage;
import com.nbug.mico.common.constants.WebSocketConstants;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 补偿WebSocket消息发送任务

 */
@Component
@Slf4j
public class WebSocketsMessageRabbitMQTask {

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 10秒执行一次
     */
    @XxlJob("webSocketsMessageRabbitMQJob")
    @TenantIgnore
    // @TenantJob // 多租户
    // @Scheduled(fixedDelay = 1000 * 10L) //10秒同步一次数据
    public void webSocketsMessageRabbitMQJob() {
        log.info("---webSocketsMessageRabbitMQJob------bargain stop status change task: Execution Time - {}", DateUtil.nowDateTime());

        if (redisUtil.exists(WebSocketConstants.WEBSOCKET_RABBITMQ_DEAD_MESSAGE)) {
            long size = redisUtil.getListSize(WebSocketConstants.WEBSOCKET_RABBITMQ_DEAD_MESSAGE);
            if (size > 0) {
                RabbitMQWebSocketMessage message = null;
                try {
                    message = (RabbitMQWebSocketMessage) redisUtil.getRightPop(WebSocketConstants.WEBSOCKET_RABBITMQ_DEAD_MESSAGE, 2000L);
                    log.debug("webSocketsMessageRabbitMQJob 补偿机制处理，消息：{}", message.toString());
                    int n = 1 / 0;
                } catch (Exception e) {
                    redisUtil.lPush(WebSocketConstants.WEBSOCKET_RABBITMQ_DEAD_MESSAGE, message);
                    log.error("webSocketsMessageRabbitMQJob 补偿机制处理失败" + " | msg : " + e.getMessage());
                }
            }
        }

    }

}
