package com.nbug.admin.task.order;

import com.nbug.common.utils.DateUtil;
import com.nbug.service.service.OrderTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 用户取消订单task任务

 */
@Component
@Configuration //读取配置
@EnableScheduling // 2.开启定时任务
public class OrderCancelTask {
    //日志
    private static final Logger logger = LoggerFactory.getLogger(OrderCancelTask.class);

    @Autowired
    private OrderTaskService orderTaskService;

    @Scheduled(fixedDelay = 1000 * 60L) //1分钟同步一次数据
    public void init() {
        logger.info("---OrderCancelTask task------produce Data with fixed rate task: Execution Time - {}", DateUtil.nowDateTime());
        try {
            orderTaskService.cancelByUser();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("OrderCancelTask.task" + " | msg : " + e.getMessage());
        }

    }
}
