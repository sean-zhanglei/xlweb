package com.nbug.module.order.job.order;

import com.nbug.depends.tenant.core.aop.TenantIgnore;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.module.order.service.OrderTaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 系统自动取消未支付订单task任务

 */
@Component
@Slf4j
public class OrderAutoCancelTask {
    //日志
    private static final Logger logger = LoggerFactory.getLogger(OrderAutoCancelTask.class);

    @Autowired
    private OrderTaskService orderTaskService;

    @XxlJob("orderAutoCancelJob")
    @TenantIgnore
    // @TenantJob // 多租户
    // @Scheduled(fixedDelay = 1000 * 60L) //1分钟同步一次数据
    public void init() {
        logger.info("---OrderAutoCancelTask task------produce Data with fixed rate task: Execution Time - {}", DateUtil.nowDateTime());
        try {
            orderTaskService.autoCancel();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("OrderAutoCancelTask.task" + " | msg : " + e.getMessage());
        }

    }
}
