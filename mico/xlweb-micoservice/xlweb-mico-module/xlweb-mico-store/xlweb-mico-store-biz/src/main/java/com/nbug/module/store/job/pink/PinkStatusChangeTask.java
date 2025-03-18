package com.nbug.module.store.job.pink;

import com.nbug.depends.tenant.core.aop.TenantIgnore;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.module.store.service.StorePinkService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 拼团状态变化Task

 */
@Component
@Slf4j
public class PinkStatusChangeTask {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(PinkStatusChangeTask.class);

    @Autowired
    private StorePinkService storePinkService;

    @XxlJob("storePinkStatusJob")
    @TenantIgnore
    // @TenantJob // 多租户
    // @Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
    public void init(){
        logger.info("---PinkStatusChange------bargain stop status change task: Execution Time - {}", DateUtil.nowDateTime());
        try {
            storePinkService.detectionStatus();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("PinkStatusChange" + " | msg : " + e.getMessage());
        }

    }
}
