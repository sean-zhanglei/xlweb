package com.nbug.module.infra.job.wechat;

import com.nbug.depends.tenant.core.aop.TenantIgnore;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.module.infra.service.sms.TemplateMessageService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 小程序消费队列消费

 */
@Component
@Slf4j
public class AsyncWeChatProgramTempMessage {
    //日志
    private static final Logger logger = LoggerFactory.getLogger(AsyncWeChatProgramTempMessage.class);

    @Autowired
    private TemplateMessageService templateMessageService;

    @XxlJob("asyncWeChatProgramTempMessageJob")
    @TenantIgnore
    // @TenantJob // 多租户
    // @Scheduled(fixedDelay = 1000 * 60L) //1分钟同步一次数据
    public void init(){
        logger.info("---AsyncWeChatProgramTempMessage task------produce Data with fixed rate task: Execution Time - {}", DateUtil.nowDate());
        try {
            templateMessageService.consumeProgram();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("AsyncWeChatProgramTempMessage.task" + " | msg : " + e.getMessage());
        }

    }
}
