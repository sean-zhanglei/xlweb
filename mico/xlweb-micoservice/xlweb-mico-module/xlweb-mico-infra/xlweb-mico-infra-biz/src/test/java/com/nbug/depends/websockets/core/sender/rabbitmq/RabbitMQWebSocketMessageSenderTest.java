package com.nbug.depends.websockets.core.sender.rabbitmq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RabbitMQWebSocketMessageSenderTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private TopicExchange topicExchange;

    @InjectMocks
    private RabbitMQWebSocketMessageSender messageSender;

    private Long userId;
    private Integer userType;
    private String messageType;
    private String messageContent;

    @BeforeEach
    public void setUp() {
        when(topicExchange.getName()).thenReturn("xlweb-mico-infra-server-websocket-exchange");
        initializeTestParameters();
    }

    private void initializeTestParameters() {
        userId = 123L;
        userType = 1;
        messageType = "testType";
        messageContent = "testContent";
    }

    @Test
    public void testSendRabbitMQMessage_SuccessfulSend_LogsSuccess() {
        // 准备
        initializeTestParameters();

        // 执行
        messageSender.send(userType, userId, messageType, messageContent);
    }

    @Test
    public void testSendRabbitMQMessage_SendFailure_LogsError() {
        // 准备
        initializeTestParameters();

        doAnswer(invocation -> {
            ((RabbitTemplate.ConfirmCallback) invocation.getArguments()[0]).confirm(null, false, "Test failure");
            return null;
        }).when(rabbitTemplate).setConfirmCallback(any(RabbitTemplate.ConfirmCallback.class));

        // 执行
        messageSender.send(userType, userId, messageType, messageContent);

    }

    @Test
    public void testSendRabbitMQMessage_ReturnedMessage_LogsError() {
        // 准备
        initializeTestParameters();

        doAnswer(invocation -> {
            ((RabbitTemplate.ReturnsCallback) invocation.getArguments()[0]).returnedMessage(null);
            return null;
        }).when(rabbitTemplate).setReturnsCallback(any(RabbitTemplate.ReturnsCallback.class));

        // 执行
        messageSender.send(userType, userId, messageType, messageContent);
    }
}
