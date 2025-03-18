package com.nbug.module.user.job.integral;

import com.nbug.depends.tenant.core.aop.TenantIgnore;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.module.user.service.UserIntegralRecordService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 积分冻结期解冻task

 */
@Component
@Slf4j
public class IntegralFrozenTask {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(IntegralFrozenTask.class);

    @Autowired
    private UserIntegralRecordService userIntegralRecordService;

    @XxlJob("integralFrozenJob")
    @TenantIgnore
    // @TenantJob // 多租户
    // @Scheduled(fixedDelay = 1000 * 60L) //1分钟同步一次数据
    public void init() {
        logger.info("---IntegralFrozenTask task------produce Data with fixed rate task: Execution Time - {}", DateUtil.nowDateTime());
        try {
            userIntegralRecordService.integralThaw();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("IntegralFrozenTask.task" + " | msg : " + e.getMessage());
        }

    }
}
