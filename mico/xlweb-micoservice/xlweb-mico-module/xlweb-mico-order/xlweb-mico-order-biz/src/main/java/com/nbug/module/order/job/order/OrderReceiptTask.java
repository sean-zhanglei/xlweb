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

/** 用户确认收货Task

 */
@Component
@Slf4j
public class OrderReceiptTask {
    //日志
    private static final Logger logger = LoggerFactory.getLogger(OrderReceiptTask.class);

    @Autowired
    private OrderTaskService orderTaskService;

    @XxlJob("orderReceiptJob")
    @TenantIgnore
    // @TenantJob // 多租户
    // @Scheduled(fixedDelay = 1000 * 60L) //1分钟同步一次数据
    public void init() {
        logger.info("---OrderReceiptTask task------produce Data with fixed rate task: Execution Time - {}", DateUtil.nowDateTime());
        try {
            orderTaskService.orderReceiving();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("OrderReceiptTask.task" + " | msg : " + e.getMessage());
        }

    }
}
