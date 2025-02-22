# 环境
1. Java Jdk1.8
2. Redis 5+
3. Mysql 5.7+

# Java项目框架
1. SpringBoot 2.2.6.RELEASE
2. Maven 3.6.1
3. Swagger 2.9.2
4. Mybatis Plus 3.3.1


ALTER TABLE `xlweb`.`eb_store_order`
ADD COLUMN `delivery_time` VARCHAR(100) NULL DEFAULT '' AFTER `out_trade_no`,
ADD COLUMN `pickup_time` VARCHAR(100) NULL DEFAULT '' AFTER `delivery_time`;
