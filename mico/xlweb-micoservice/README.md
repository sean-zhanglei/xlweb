# 环境
1. Java Jdk1.8
2. Redis 5+ 测试版本7
3. Mysql 5.7+  测试版本8.0.18

# 微服务组件
1. SpringCloudGateWay 网关 3.1.9
2. Nacos 注册配置中心 2021.0.6.2
3. OpenFeign 分布式调用 3.1.9
4. Loadbalancer 客户端负载均衡 3.1.8
5. okhttp http客户端 11.10
6. Sentinel 限流
7. Seata 分布式事务
8. PinPoint 调用链路监控
9. xxl-job 定时任务 2.4.0
10. redission 分布式缓存/队列 3.41.0
11. MQ 消息队列 kafka 2.8.11 rabbit 2.4.17  rocketmq 2.3.1


# Java项目框架
1. SpringBoot 2.7.18
2. SpringCloud 2021.0.9
3. SpringCloud Alibaba 2021.0.6.2
4. SpringFramework 5.3.39
4. Maven 3.6.1
5. openApi 1.7.0
6. Mybatis Plus 3.5.9
7. guava 33.4.0-jre
8. Junit 4.12


ALTER TABLE `xlweb-mico`.`eb_store_order`
ADD COLUMN `delivery_time` VARCHAR(100) NULL DEFAULT '' AFTER `out_trade_no`,
ADD COLUMN `pickup_time` VARCHAR(100) NULL DEFAULT '' AFTER `delivery_time`;
