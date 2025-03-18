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
 * 订单自动完成Task

 */
@Component
@Slf4j
public class OrderAutoCompleteTask {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(OrderCompleteTask.class);

    @Autowired
    private OrderTaskService orderTaskService;

    @XxlJob("orderAutoCancelJob")
    @TenantIgnore
    // @TenantJob // 多租户
    // @Scheduled(fixedDelay = 1000 * 60L * 60) //每小时同步一次数据
    public void init() {
        logger.info("---OrderAutoCompleteTask task------produce Data with fixed rate task: Execution Time - {}", DateUtil.nowDateTime());
        try {
            orderTaskService.autoComplete();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("OrderAutoCompleteTask.task" + " | msg : " + e.getMessage());
        }

    }

}
