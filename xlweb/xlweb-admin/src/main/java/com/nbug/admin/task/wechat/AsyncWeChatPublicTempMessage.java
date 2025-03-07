package com.nbug.admin.task.wechat;

import com.nbug.common.utils.DateUtil;
import com.nbug.service.service.TemplateMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 公众号消费队列消费

 */
@Component
@Configuration //读取配置
@EnableScheduling // 2.开启定时任务
public class AsyncWeChatPublicTempMessage {
    //日志
    private static final Logger logger = LoggerFactory.getLogger(AsyncWeChatPublicTempMessage.class);

    @Autowired
    private TemplateMessageService templateMessageService;

    @Scheduled(fixedDelay = 1000 * 60L) //1分钟同步一次数据
    public void init() {
        logger.info("---AsyncWeChatPublicTempMessage task------produce Data with fixed rate task: Execution Time - {}", DateUtil.nowDate());
        try {
            templateMessageService.consumePublic();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("AsyncWeChatPublicTempMessage.task" + " | msg : " + e.getMessage());
        }

    }
}

