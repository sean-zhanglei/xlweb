# standalone  ����汾

# ����
1. Java Jdk1.8
2. Redis 5+
3. Mysql 5.7+

# Java��Ŀ���
1. SpringBoot 2.2.6.RELEASE
2. Maven 3.6.1
3. Swagger 2.9.2
4. Mybatis Plus 3.3.1


ALTER TABLE `xlweb`.`eb_store_order`
ADD COLUMN `delivery_time` VARCHAR(100) NULL DEFAULT '' AFTER `out_trade_no`,
ADD COLUMN `pickup_time` VARCHAR(100) NULL DEFAULT '' AFTER `delivery_time`;



# mico ΢����汾


# ����
1. Java Jdk1.8
2. Redis 5+ ���԰汾7
3. Mysql 5.7+  ���԰汾8.0.18
4. Flyway ���֧��Mysql�İ汾 8.2.0  ���°汾��ʹ�ò����� https://github.com/flyway/flyway/releases/tag/v9.22.1

# ΢�������
1. SpringCloudGateWay ���� 3.1.9
2. Nacos ע���������� 2.2.4-OEM �ͻ��� 2021.0.6.2
3. OpenFeign �ֲ�ʽ���� 3.1.9
4. Loadbalancer �ͻ��˸��ؾ��� 3.1.8
5. okhttp http�ͻ��� 11.10
6. Sentinel ����
7. Seata �ֲ�ʽ���� 2.1.0
8. PinPoint ������·��� ��ʱδ���룬ʹ��Skywalking
9. xxl-job ��ʱ���� 2.4.0
10. redission �ֲ�ʽ����/���� 3.41.0
11. MQ ��Ϣ���� kafka 2.8.11 rabbit 2.4.17  rocketmq 2.3.1
12. Skywalking 8.9.0


# Java��Ŀ���
1. SpringBoot 2.7.18
2. SpringCloud 2021.0.9
3. SpringCloud Alibaba 2021.0.6.2
4. SpringFramework 5.3.39
4. Maven 3.6.1
5. openApi 1.7.0
6. Mybatis Plus 3.5.9
7. guava 33.4.0-jre
8. Junit 4.12

# ����˳��
1. nacos��xxl-job
2. gateway
3. infra-service��system-server��user-service��order-service��store-service



# mico-ddd ΢��������ģ�汾