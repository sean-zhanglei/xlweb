package com.nbug.module.user.job.brokerage;


import com.nbug.depends.tenant.core.aop.TenantIgnore;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.module.user.service.UserBrokerageRecordService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 佣金冻结期解冻task

 */
@Component
@Slf4j
public class BrokerageFrozenTask {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(BrokerageFrozenTask.class);

    @Autowired
    private UserBrokerageRecordService userBrokerageRecordService;

    @XxlJob("brokerageFrozenJob")
    @TenantIgnore
    // @TenantJob // 多租户
    // @Scheduled(fixedDelay = 1000 * 60L) //1分钟同步一次数据
    public void init(){
        logger.info("---BrokerageFrozenTask task------produce Data with fixed rate task: Execution Time - {}", DateUtil.nowDateTime());
        try {
            userBrokerageRecordService.brokerageThaw();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("BrokerageFrozenTask.task" + " | msg : " + e.getMessage());
        }

    }
}
