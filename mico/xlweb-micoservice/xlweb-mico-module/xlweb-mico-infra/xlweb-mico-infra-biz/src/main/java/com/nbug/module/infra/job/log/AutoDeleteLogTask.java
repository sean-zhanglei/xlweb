package com.nbug.module.infra.job.log;

import com.nbug.depends.tenant.core.aop.TenantIgnore;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.module.infra.service.wechat.WechatExceptionsService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自动删除不需要的历史日志

 */
@Component
@Slf4j
public class AutoDeleteLogTask {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(AutoDeleteLogTask.class);

    @Autowired
    private WechatExceptionsService wechatExceptionsService;


    /**
     * 每天0点执行
     */
    @XxlJob("autoDeleteLogJob")
    @TenantIgnore
    // @TenantJob // 多租户
    // @Scheduled(cron = "0 0 0 */1 * ?")
    public void autoDeleteLog() {
        // cron : 0 0 0 */1 * ?
        logger.info("---AutoDeleteLogTask------bargain stop status change task: Execution Time - {}", DateUtil.nowDateTime());
        try {
            wechatExceptionsService.autoDeleteLog();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("AutoDeleteLogTask" + " | msg : " + e.getMessage());
        }
    }

}
