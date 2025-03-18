package com.nbug.module.store.job.bargain;

import com.nbug.depends.tenant.core.aop.TenantIgnore;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.module.store.service.StoreBargainService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 砍价活动结束状态变化定时任务

 */
@Component
@Slf4j
public class BargainStopChangeTask {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(BargainStopChangeTask.class);

    @Autowired
    private StoreBargainService storeBargainService;

    @XxlJob("storeBargainStopChangeJob")
    @TenantIgnore
    // @TenantJob // 多租户
    // @Scheduled(cron = "0 0 0 */1 * ?") //5秒钟同步一次数据
    public void init(){
        logger.info("---BargainStopChangeTask------bargain stop status change task: Execution Time - {}", DateUtil.nowDateTime());
        try {
            storeBargainService.stopAfterChange();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("BargainStopChangeTask" + " | msg : " + e.getMessage());
        }

    }

}
