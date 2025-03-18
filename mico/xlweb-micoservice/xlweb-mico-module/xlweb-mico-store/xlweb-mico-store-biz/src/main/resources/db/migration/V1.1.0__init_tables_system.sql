--
-- Table structure for table `eb_store_bargain`
--

DROP TABLE IF EXISTS `eb_store_bargain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_bargain` (
                                    `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '砍价商品ID',
                                    `product_id` int(11) unsigned NOT NULL COMMENT '关联商品ID',
                                    `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '砍价活动名称',
                                    `image` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '砍价活动图片',
                                    `unit_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '单位名称',
                                    `stock` int(11) unsigned DEFAULT NULL COMMENT '库存',
                                    `sales` int(11) unsigned DEFAULT NULL COMMENT '销量',
                                    `images` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '砍价商品轮播图',
                                    `start_time` bigint(14) unsigned NOT NULL COMMENT '砍价开启时间',
                                    `stop_time` bigint(14) unsigned NOT NULL COMMENT '砍价结束时间',
                                    `store_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '砍价商品名称',
                                    `price` decimal(8,2) unsigned DEFAULT NULL COMMENT '砍价金额',
                                    `min_price` decimal(8,2) unsigned DEFAULT NULL COMMENT '砍价商品最低价',
                                    `num` int(11) unsigned DEFAULT NULL COMMENT '购买数量限制——单个活动每个用户发起砍价次数限制',
                                    `bargain_max_price` decimal(8,2) unsigned DEFAULT NULL COMMENT '用户每次砍价的最大金额',
                                    `bargain_min_price` decimal(8,2) unsigned DEFAULT NULL COMMENT '用户每次砍价的最小金额',
                                    `bargain_num` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '帮砍次数——单个商品用户可以帮砍的次数',
                                    `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '砍价状态 0(到砍价时间不自动开启)  1(到砍价时间自动开启时间)',
                                    `give_integral` int(11) DEFAULT '0' COMMENT '反多少积分',
                                    `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '砍价活动简介',
                                    `cost` decimal(8,2) unsigned DEFAULT NULL COMMENT '成本价',
                                    `sort` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
                                    `is_hot` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否推荐0不推荐1推荐',
                                    `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除 1删除',
                                    `add_time` bigint(14) unsigned DEFAULT NULL COMMENT '添加时间',
                                    `is_postage` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否包邮 0不包邮 1包邮',
                                    `postage` decimal(10,2) unsigned DEFAULT NULL COMMENT '邮费',
                                    `rule` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '砍价规则',
                                    `look` int(11) unsigned DEFAULT '0' COMMENT '砍价商品浏览量',
                                    `share` int(11) unsigned DEFAULT '0' COMMENT '砍价商品分享量',
                                    `temp_id` int(11) DEFAULT NULL COMMENT '运费模板ID',
                                    `weight` decimal(8,2) DEFAULT '0.00' COMMENT '重量',
                                    `volume` decimal(8,2) DEFAULT '0.00' COMMENT '体积',
                                    `quota` int(10) NOT NULL DEFAULT '0' COMMENT '限购总数',
                                    `quota_show` int(10) NOT NULL DEFAULT '0' COMMENT '限量总数显示',
                                    `people_num` int(11) DEFAULT NULL COMMENT '砍价人数——需要多少人砍价成功',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='砍价表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_bargain`
--

/*!40000 ALTER TABLE `eb_store_bargain` DISABLE KEYS */;
INSERT INTO `eb_store_bargain` VALUES (1,3,'万事利秋冬新品时尚百搭经典真丝绒厚款围巾 格物致知蓝咖','xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','件',78,0,'[\"xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg\"]',1640361600000,1646063999999,'万事利秋冬新品时尚百搭经典真丝绒厚款围巾 格物致知蓝咖',374.00,324.00,2,NULL,NULL,2,1,0,NULL,89.00,0,0,0,1640409149614,1,NULL,NULL,0,0,1,0.00,0.00,66,66,2),(2,1,'LOFREE洛斐 奶茶无线蓝牙键鼠套装','xlwebimage/public/maintain/2021/12/25/63969148b6c4447d918124fd810c1da5m2h9aiylly.jpg','个',345,0,'[\"xlwebimage/public/maintain/2021/12/25/63969148b6c4447d918124fd810c1da5m2h9aiylly.jpg\"]',1640361600000,1646063999999,'LOFREE洛斐 奶茶无线蓝牙键鼠套装',268.00,226.00,1,NULL,NULL,2,1,0,NULL,40.00,0,0,0,1640409242804,1,NULL,NULL,0,0,1,0.00,0.00,69,69,2);
/*!40000 ALTER TABLE `eb_store_bargain` ENABLE KEYS */;

--
-- Table structure for table `eb_store_bargain_user`
--

DROP TABLE IF EXISTS `eb_store_bargain_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_bargain_user` (
                                         `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户参与砍价表ID',
                                         `uid` int(11) unsigned DEFAULT NULL COMMENT '用户ID',
                                         `bargain_id` int(11) unsigned DEFAULT NULL COMMENT '砍价商品id',
                                         `bargain_price_min` decimal(8,2) unsigned DEFAULT NULL COMMENT '砍价的最低价',
                                         `bargain_price` decimal(8,2) DEFAULT NULL COMMENT '砍价金额',
                                         `price` decimal(8,2) unsigned DEFAULT NULL COMMENT '砍掉的价格',
                                         `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态 1参与中 2 活动结束参与失败 3活动结束参与成功',
                                         `add_time` bigint(14) unsigned DEFAULT NULL COMMENT '参与时间',
                                         `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否取消',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户参与砍价表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_bargain_user`
--


/*!40000 ALTER TABLE `eb_store_bargain_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_bargain_user` ENABLE KEYS */;


--
-- Table structure for table `eb_store_bargain_user_help`
--

DROP TABLE IF EXISTS `eb_store_bargain_user_help`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_bargain_user_help` (
                                              `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '砍价用户帮助表ID',
                                              `uid` int(11) unsigned DEFAULT NULL COMMENT '帮助的用户id',
                                              `bargain_id` int(11) unsigned DEFAULT NULL COMMENT '砍价商品ID',
                                              `bargain_user_id` int(11) unsigned DEFAULT NULL COMMENT '用户参与砍价表id',
                                              `price` decimal(8,2) unsigned DEFAULT NULL COMMENT '帮助砍价多少金额',
                                              `add_time` bigint(14) unsigned DEFAULT NULL COMMENT '添加时间',
                                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='砍价用户帮助表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_bargain_user_help`
--


/*!40000 ALTER TABLE `eb_store_bargain_user_help` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_bargain_user_help` ENABLE KEYS */;


--
-- Table structure for table `eb_store_cart`
--

DROP TABLE IF EXISTS `eb_store_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_cart` (
                                 `id` bigint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '购物车表ID',
                                 `uid` int(10) unsigned NOT NULL COMMENT '用户ID',
                                 `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型',
                                 `product_id` int(10) unsigned NOT NULL COMMENT '商品ID',
                                 `product_attr_unique` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '商品属性',
                                 `cart_num` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '商品数量',
                                 `is_new` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为立即购买',
                                 `combination_id` int(11) unsigned DEFAULT '0' COMMENT '拼团id',
                                 `seckill_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '秒杀商品ID',
                                 `bargain_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '砍价id',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                                 `update_time` timestamp NULL DEFAULT NULL COMMENT 'g',
                                 `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '购物车状态',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `user_id` (`uid`) USING BTREE,
                                 KEY `goods_id` (`product_id`) USING BTREE,
                                 KEY `uid` (`uid`) USING BTREE,
                                 KEY `uid_2` (`uid`) USING BTREE,
                                 KEY `uid_3` (`uid`,`is_new`) USING BTREE,
                                 KEY `type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='购物车表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_cart`
--


/*!40000 ALTER TABLE `eb_store_cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_cart` ENABLE KEYS */;


--
-- Table structure for table `eb_store_combination`
--

DROP TABLE IF EXISTS `eb_store_combination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_combination` (
                                        `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '拼团商品ID',
                                        `product_id` int(10) unsigned NOT NULL COMMENT '商品id',
                                        `mer_id` int(10) unsigned DEFAULT '0' COMMENT '商户id',
                                        `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '推荐图',
                                        `images` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '轮播图',
                                        `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动标题',
                                        `attr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '活动属性',
                                        `people` int(2) unsigned NOT NULL COMMENT '参团人数',
                                        `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '简介',
                                        `price` decimal(10,2) unsigned NOT NULL COMMENT '价格',
                                        `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
                                        `sales` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '销量',
                                        `stock` int(10) unsigned NOT NULL COMMENT '库存',
                                        `add_time` bigint(14) NOT NULL COMMENT '添加时间',
                                        `is_host` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '推荐',
                                        `is_show` tinyint(1) unsigned NOT NULL COMMENT '商品状态',
                                        `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0',
                                        `combination` tinyint(1) unsigned DEFAULT '1',
                                        `mer_use` tinyint(1) unsigned DEFAULT NULL COMMENT '商户是否可用1可用0不可用',
                                        `is_postage` tinyint(1) unsigned DEFAULT NULL COMMENT '是否包邮1是0否',
                                        `postage` decimal(10,2) unsigned DEFAULT NULL COMMENT '邮费',
                                        `start_time` bigint(14) unsigned NOT NULL COMMENT '拼团开始时间',
                                        `stop_time` bigint(14) unsigned NOT NULL COMMENT '拼团结束时间',
                                        `effective_time` int(11) NOT NULL DEFAULT '0' COMMENT '拼团订单有效时间(小时)',
                                        `cost` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '拼图商品成本',
                                        `browse` int(11) DEFAULT '0' COMMENT '浏览量',
                                        `unit_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '单位名',
                                        `temp_id` int(11) NOT NULL COMMENT '运费模板ID',
                                        `weight` decimal(8,2) DEFAULT '0.00' COMMENT '重量',
                                        `volume` decimal(8,2) DEFAULT '0.00' COMMENT '体积',
                                        `num` int(11) DEFAULT NULL COMMENT '单次购买数量',
                                        `quota` int(10) NOT NULL DEFAULT '0' COMMENT '限购总数',
                                        `quota_show` int(10) NOT NULL DEFAULT '0' COMMENT '限量总数显示',
                                        `ot_price` decimal(10,2) NOT NULL COMMENT '原价',
                                        `once_num` int(11) NOT NULL DEFAULT '0' COMMENT '每个订单可购买数量',
                                        `virtual_ration` int(11) NOT NULL DEFAULT '100' COMMENT '虚拟成团百分比',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='拼团商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_combination`
--


/*!40000 ALTER TABLE `eb_store_combination` DISABLE KEYS */;
INSERT INTO `eb_store_combination` VALUES (1,7,0,'xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg','[\"xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg\"]','车载小鹿首饰架摆件鹿角树形耳钉首饰展示架耳环架手链饰品收纳首饰挂件 白色',NULL,2,'车载小鹿首饰架摆件鹿角树形耳钉首饰展示架耳环架手链饰品收纳首饰挂件 白色',13.00,0,0,666,1640409319713,0,0,1,1,NULL,NULL,NULL,1640361600000,1646063999999,1,5.00,0,'个',1,0.00,0.00,2,66,66,18.00,1,0),(2,6,0,'xlwebimage/public/maintain/2021/12/25/fef1aee22a04466d93693f5791d44bfd2rsm469mcr.jpg','[\"xlwebimage/public/maintain/2021/12/25/fef1aee22a04466d93693f5791d44bfd2rsm469mcr.jpg\"]','第一江南 西湖龙井茶叶礼盒 明前特级龙井绿茶【至臻献礼200g】礼盒装',NULL,2,'第一江南 西湖龙井茶叶礼盒 明前特级龙井绿茶【至臻献礼200g】礼盒装',1288.00,0,0,999,1640409356986,0,0,1,1,NULL,NULL,NULL,1640361600000,1646063999999,1,300.00,0,'件',1,0.00,0.00,1,99,99,1688.00,1,0),(3,7,0,'xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg','[\"xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg\"]','车载小鹿首饰架摆件鹿角树形耳钉首饰展示架耳环架手链饰品收纳首饰挂件 白色',NULL,2,'车载小鹿首饰架摆件鹿角树形耳钉首饰展示架耳环架手链饰品收纳首饰挂件 白色',8.00,0,0,1533,1640749674852,0,1,0,1,NULL,NULL,NULL,1640707200000,1643644799999,1,5.00,0,'个',1,0.00,0.00,1,151,151,18.00,1,0),(4,3,0,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','[\"xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg\"]','万事利秋冬新品时尚百搭经典真丝绒厚款围巾 格物致知蓝咖',NULL,2,'万事利秋冬新品时尚百搭经典真丝绒厚款围巾 格物致知蓝咖',324.00,0,1,1865,1640749726595,0,1,0,1,NULL,NULL,NULL,1640707200000,1643644799999,1,100.00,0,'件',1,0.00,0.00,1,183,184,412.00,1,0);
/*!40000 ALTER TABLE `eb_store_combination` ENABLE KEYS */;


--
-- Table structure for table `eb_store_coupon`
--

DROP TABLE IF EXISTS `eb_store_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_coupon` (
                                   `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '优惠券表ID',
                                   `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '优惠券名称',
                                   `money` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '兑换的优惠券面值',
                                   `is_limited` tinyint(1) DEFAULT '0' COMMENT '是否限量, 默认0 不限量， 1限量',
                                   `total` int(11) NOT NULL DEFAULT '0' COMMENT '发放总数',
                                   `last_total` int(11) DEFAULT '0' COMMENT '剩余数量',
                                   `use_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '使用类型 1 全场通用, 2 商品券, 3 品类券',
                                   `primary_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '所属商品id / 分类id',
                                   `min_price` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '最低消费，0代表不限制',
                                   `receive_start_time` timestamp NOT NULL COMMENT '可领取开始时间',
                                   `receive_end_time` timestamp NULL DEFAULT NULL COMMENT '可领取结束时间',
                                   `is_fixed_time` tinyint(1) DEFAULT '0' COMMENT '是否固定使用时间, 默认0 否， 1是',
                                   `use_start_time` timestamp NULL DEFAULT NULL COMMENT '可使用时间范围 开始时间',
                                   `use_end_time` timestamp NULL DEFAULT NULL COMMENT '可使用时间范围 结束时间',
                                   `day` int(4) DEFAULT '0' COMMENT '天数',
                                   `type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '优惠券类型 1 手动领取, 2 新人券, 3 赠送券',
                                   `sort` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '排序',
                                   `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态（0：关闭，1：开启）',
                                   `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除 状态（0：否，1：是）',
                                   `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   KEY `state` (`status`) USING BTREE,
                                   KEY `is_del` (`is_del`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='优惠券表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_coupon`
--


/*!40000 ALTER TABLE `eb_store_coupon` DISABLE KEYS */;
INSERT INTO `eb_store_coupon` VALUES (1,'冬天快乐',5.00,1,20,16,1,'',0.00,'2021-12-24 16:00:00','2022-01-06 16:00:00',0,'2021-12-30 09:19:57','2022-01-06 09:19:57',7,1,0,1,0,'2021-12-25 06:35:42','2021-12-25 06:35:42'),(2,'圣诞快乐',10.00,1,30,26,1,'',0.00,'2021-12-24 16:00:00','2022-01-11 16:00:00',0,'2021-12-30 09:19:44','2022-01-19 09:19:44',20,1,0,1,0,'2021-12-25 06:36:30','2021-12-30 08:56:36');
/*!40000 ALTER TABLE `eb_store_coupon` ENABLE KEYS */;


--
-- Table structure for table `eb_store_coupon_user`
--

DROP TABLE IF EXISTS `eb_store_coupon_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_coupon_user` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                        `coupon_id` int(10) NOT NULL COMMENT '优惠券发布id',
                                        `cid` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '兑换的项目id',
                                        `uid` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '领取人id',
                                        `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '优惠券名称',
                                        `money` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '优惠券的面值',
                                        `min_price` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '最低消费多少金额可用优惠券',
                                        `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'send' COMMENT '获取方式，send后台发放, 用户领取 get',
                                        `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0：未使用，1：已使用, 2:已失效）',
                                        `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        `start_time` timestamp NULL DEFAULT NULL COMMENT '开始使用时间',
                                        `end_time` timestamp NULL DEFAULT NULL COMMENT '过期时间',
                                        `use_time` timestamp NULL DEFAULT NULL COMMENT '使用时间',
                                        `use_type` tinyint(1) DEFAULT '1' COMMENT '使用类型 1 全场通用, 2 商品券, 3 品类券',
                                        `primary_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所属商品id / 分类id',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        KEY `cid` (`cid`) USING BTREE,
                                        KEY `uid` (`uid`) USING BTREE,
                                        KEY `end_time` (`end_time`) USING BTREE,
                                        KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='优惠券记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_coupon_user`
--


/*!40000 ALTER TABLE `eb_store_coupon_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_coupon_user` ENABLE KEYS */;


--
-- Table structure for table `eb_store_order`
--

DROP TABLE IF EXISTS `eb_store_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_order` (
                                  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
                                  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
                                  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
                                  `real_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户姓名',
                                  `user_phone` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户电话',
                                  `user_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详细地址',
                                  `freight_price` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '运费金额',
                                  `total_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '订单商品总数',
                                  `total_price` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '订单总价',
                                  `total_postage` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '邮费',
                                  `pay_price` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '实际支付金额',
                                  `pay_postage` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '支付邮费',
                                  `deduction_price` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '抵扣金额',
                                  `coupon_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '优惠券id',
                                  `coupon_price` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '优惠券金额',
                                  `paid` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '支付状态',
                                  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
                                  `pay_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付方式',
                                  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单状态（0：待发货；1：待收货；2：已收货，待评价；3：已完成；）',
                                  `refund_status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0 未退款 1 申请中 2 已退款 3 退款中',
                                  `refund_reason_wap_img` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '退款图片',
                                  `refund_reason_wap_explain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '退款用户说明',
                                  `refund_reason_wap` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '前台退款原因',
                                  `refund_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '不退款的理由',
                                  `refund_reason_time` timestamp NULL DEFAULT NULL COMMENT '退款时间',
                                  `refund_price` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '退款金额',
                                  `delivery_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '快递名称/送货人姓名',
                                  `delivery_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发货类型',
                                  `delivery_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '快递单号/手机号',
                                  `gain_integral` int(11) DEFAULT '0' COMMENT '消费赚取积分',
                                  `use_integral` int(11) DEFAULT '0' COMMENT '使用积分',
                                  `back_integral` int(11) DEFAULT '0' COMMENT '给用户退了多少积分',
                                  `mark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
                                  `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
                                  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '管理员备注',
                                  `mer_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商户ID',
                                  `is_mer_check` tinyint(3) unsigned NOT NULL DEFAULT '0',
                                  `combination_id` int(11) unsigned DEFAULT '0' COMMENT '拼团商品id0一般商品',
                                  `pink_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '拼团id 0没有拼团',
                                  `cost` decimal(8,2) unsigned NOT NULL COMMENT '成本价',
                                  `seckill_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '秒杀商品ID',
                                  `bargain_id` int(11) unsigned DEFAULT '0' COMMENT '砍价id',
                                  `verify_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '核销码',
                                  `store_id` int(11) NOT NULL DEFAULT '0' COMMENT '门店id',
                                  `shipping_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '配送方式 1=快递 ，2=门店自提',
                                  `clerk_id` int(11) NOT NULL DEFAULT '0' COMMENT '店员id/核销员id',
                                  `is_channel` tinyint(1) unsigned DEFAULT '0' COMMENT '支付渠道(0微信公众号1微信小程序2余额)',
                                  `is_remind` tinyint(1) unsigned DEFAULT '0' COMMENT '消息提醒',
                                  `is_system_del` tinyint(1) DEFAULT '0' COMMENT '后台是否删除',
                                  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `delivery_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '快递公司简称',
                                  `bargain_user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户拼团活动id 0没有',
                                  `type` int(3) NOT NULL DEFAULT '0' COMMENT '订单类型:0-普通订单，1-视频号订单',
                                  `pro_total_price` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '商品总价',
                                  `before_pay_price` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '改价前支付金额',
                                  `is_alter_price` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否改价,0-否，1-是',
                                  `out_trade_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  UNIQUE KEY `order_id_2` (`order_id`,`uid`) USING BTREE,
                                  KEY `uid` (`uid`) USING BTREE,
                                  KEY `add_time` (`create_time`) USING BTREE,
                                  KEY `pay_price` (`pay_price`) USING BTREE,
                                  KEY `paid` (`paid`) USING BTREE,
                                  KEY `pay_time` (`pay_time`) USING BTREE,
                                  KEY `pay_type` (`pay_type`) USING BTREE,
                                  KEY `status` (`status`) USING BTREE,
                                  KEY `is_del` (`is_del`) USING BTREE,
                                  KEY `coupon_id` (`coupon_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_order`
--


/*!40000 ALTER TABLE `eb_store_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_order` ENABLE KEYS */;


--
-- Table structure for table `eb_store_order_info`
--

DROP TABLE IF EXISTS `eb_store_order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_order_info` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                       `order_id` int(11) unsigned NOT NULL COMMENT '订单id',
                                       `product_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商品ID',
                                       `info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '购买东西的详细信息',
                                       `unique` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '唯一id',
                                       `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
                                       `product_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
                                       `attr_value_id` int(11) unsigned DEFAULT NULL COMMENT '规格属性值id',
                                       `image` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品图片',
                                       `sku` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品sku',
                                       `price` decimal(8,2) unsigned NOT NULL COMMENT '商品价格',
                                       `pay_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '购买数量',
                                       `weight` decimal(8,2) unsigned NOT NULL COMMENT '重量',
                                       `volume` decimal(8,2) unsigned NOT NULL COMMENT '体积',
                                       `give_integral` int(11) unsigned NOT NULL COMMENT '赠送积分',
                                       `is_reply` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否评价，0-未评价，1-已评价',
                                       `is_sub` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否单独分佣,0-否，1-是',
                                       `vip_price` decimal(8,2) unsigned NOT NULL COMMENT '会员价',
                                       `product_type` int(2) NOT NULL DEFAULT '0' COMMENT '商品类型:0-普通，1-秒杀，2-砍价，3-拼团，4-视频号',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       UNIQUE KEY `oid` (`order_id`,`unique`) USING BTREE,
                                       KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='订单购物详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_order_info`
--


/*!40000 ALTER TABLE `eb_store_order_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_order_info` ENABLE KEYS */;


--
-- Table structure for table `eb_store_order_status`
--

DROP TABLE IF EXISTS `eb_store_order_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_order_status` (
                                         `oid` int(10) unsigned NOT NULL COMMENT '订单id',
                                         `change_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作类型',
                                         `change_message` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作备注',
                                         `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
                                         KEY `oid` (`oid`) USING BTREE,
                                         KEY `change_type` (`change_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='订单操作记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_order_status`
--


/*!40000 ALTER TABLE `eb_store_order_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_order_status` ENABLE KEYS */;


--
-- Table structure for table `eb_store_pink`
--

DROP TABLE IF EXISTS `eb_store_pink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_pink` (
                                 `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '拼团ID',
                                 `uid` int(10) unsigned NOT NULL COMMENT '用户id',
                                 `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单id 生成',
                                 `order_id_key` int(10) unsigned NOT NULL COMMENT '订单id  数据库',
                                 `total_num` int(10) unsigned NOT NULL COMMENT '购买商品个数',
                                 `total_price` decimal(10,2) unsigned NOT NULL COMMENT '购买总金额',
                                 `cid` int(10) unsigned NOT NULL COMMENT '拼团商品id',
                                 `pid` int(10) unsigned NOT NULL COMMENT '商品id',
                                 `people` int(10) unsigned NOT NULL COMMENT '拼图总人数',
                                 `price` decimal(10,2) unsigned NOT NULL COMMENT '拼团商品单价',
                                 `add_time` bigint(14) NOT NULL COMMENT '开始时间',
                                 `stop_time` bigint(14) NOT NULL COMMENT '结束时间',
                                 `k_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '团长id 0为团长',
                                 `is_tpl` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否发送模板消息0未发送1已发送',
                                 `is_refund` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否退款 0未退款 1已退款',
                                 `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态1进行中2已完成3未完成',
                                 `is_virtual` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否虚拟拼团',
                                 `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
                                 `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户头像',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='拼团表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_pink`
--


/*!40000 ALTER TABLE `eb_store_pink` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_pink` ENABLE KEYS */;


--
-- Table structure for table `eb_store_product`
--

DROP TABLE IF EXISTS `eb_store_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_product` (
                                    `id` mediumint(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
                                    `mer_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商户Id(0为总后台管理员创建,不为0的时候是商户后台创建)',
                                    `image` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品图片',
                                    `slider_image` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '轮播图',
                                    `store_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
                                    `store_info` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品简介',
                                    `keyword` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关键字',
                                    `bar_code` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '商品条码（一维码）',
                                    `cate_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类id',
                                    `price` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '商品价格',
                                    `vip_price` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '会员价格',
                                    `ot_price` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '市场价',
                                    `postage` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '邮费',
                                    `unit_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单位名',
                                    `sort` smallint(11) NOT NULL DEFAULT '0' COMMENT '排序',
                                    `sales` mediumint(11) unsigned NOT NULL DEFAULT '0' COMMENT '销量',
                                    `stock` mediumint(11) unsigned NOT NULL DEFAULT '0' COMMENT '库存',
                                    `is_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（0：未上架，1：上架）',
                                    `is_hot` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否热卖',
                                    `is_benefit` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否优惠',
                                    `is_best` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否精品',
                                    `is_new` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否新品',
                                    `add_time` int(11) unsigned NOT NULL COMMENT '添加时间',
                                    `is_postage` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否包邮',
                                    `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
                                    `mer_use` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '商户是否代理 0不可代理1可代理',
                                    `give_integral` int(11) DEFAULT '0' COMMENT '获得积分',
                                    `cost` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '成本价',
                                    `is_seckill` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '秒杀状态 0 未开启 1已开启',
                                    `is_bargain` tinyint(1) unsigned DEFAULT NULL COMMENT '砍价状态 0未开启 1开启',
                                    `is_good` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否优品推荐',
                                    `is_sub` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否单独分佣',
                                    `ficti` mediumint(11) DEFAULT '100' COMMENT '虚拟销量',
                                    `browse` int(11) DEFAULT '0' COMMENT '浏览量',
                                    `code_path` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '商品二维码地址(用户小程序海报)',
                                    `soure_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '淘宝京东1688类型',
                                    `video_link` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主图视频链接',
                                    `temp_id` int(11) NOT NULL DEFAULT '1' COMMENT '运费模板ID',
                                    `spec_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '规格 0单 1多',
                                    `activity` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '活动显示排序0=默认, 1=秒杀，2=砍价，3=拼团',
                                    `flat_pattern` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '展示图',
                                    `is_recycle` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否回收站',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    KEY `cate_id` (`cate_id`) USING BTREE,
                                    KEY `is_hot` (`is_hot`) USING BTREE,
                                    KEY `is_benefit` (`is_benefit`) USING BTREE,
                                    KEY `is_best` (`is_best`) USING BTREE,
                                    KEY `is_new` (`is_new`) USING BTREE,
                                    KEY `toggle_on_sale, is_del` (`is_del`) USING BTREE,
                                    KEY `price` (`price`) USING BTREE,
                                    KEY `is_show` (`is_show`) USING BTREE,
                                    KEY `sort` (`sort`) USING BTREE,
                                    KEY `sales` (`sales`) USING BTREE,
                                    KEY `add_time` (`add_time`) USING BTREE,
                                    KEY `is_postage` (`is_postage`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_product`
--


/*!40000 ALTER TABLE `eb_store_product` DISABLE KEYS */;
INSERT INTO `eb_store_product` VALUES (1,0,'xlwebimage/public/maintain/2021/12/25/63969148b6c4447d918124fd810c1da5m2h9aiylly.jpg','[\"xlwebimage/public/maintain/2021/12/25/63969148b6c4447d918124fd810c1da5m2h9aiylly.jpg\"]','LOFREE洛斐 奶茶无线蓝牙键鼠套装','LOFREE洛斐 奶茶无线蓝牙键鼠套装','无线蓝牙 键鼠 套装','','246,248,258,488',268.00,0.00,188.00,0.00,'个',0,0,345,1,1,1,1,1,1640407859,0,0,0,0,40.00,0,NULL,0,0,66,2,'','','',1,0,'0,1,2,3','',0),(2,0,'xlwebimage/public/maintain/2021/12/25/49ae68efce4848a185a8ddcb4e5c95a2zl2dlwi77v.jpg','[\"xlwebimage/public/maintain/2021/12/25/49ae68efce4848a185a8ddcb4e5c95a2zl2dlwi77v.jpg\"]','欧普照明（OPPLE）LED卧室床头壁灯 温馨浪漫美式风格墙壁灯','欧普照明（OPPLE）LED卧室床头壁灯 温馨浪漫美式风格墙壁灯','温馨浪漫美式风格墙壁灯','','276',99.00,0.00,129.00,0.00,'件',0,0,88,1,0,0,1,1,1640408014,0,0,0,0,20.00,0,NULL,0,0,88,0,'','','',1,0,'0,1,2,3','',0),(3,0,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','[\"xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg\"]','万事利秋冬新品时尚百搭经典真丝绒厚款围巾 格物致知蓝咖','万事利秋冬新品时尚百搭经典真丝绒厚款围巾 格物致知蓝咖','万事利秋冬新品时尚百搭经典真丝绒厚款围巾 格物致知蓝咖','','246,248,258,488,289',374.00,0.00,412.00,0.00,'件',0,1,1865,1,1,1,1,1,1640408162,0,0,0,0,100.00,0,NULL,0,0,68,3,'','','',1,1,'0,1,2,3','',0),(4,0,'xlwebimage/public/maintain/2021/12/25/3ed152917e3f4f7faa9414050b6cab49vuadulntb8.jpg','[\"xlwebimage/public/maintain/2021/12/25/3ed152917e3f4f7faa9414050b6cab49vuadulntb8.jpg\"]','CAMILA&KORALI品牌包包女包斜挎单肩小包女式','CAMILA&KORALI品牌包包女包斜挎单肩小包女式','CAMILA&KORALI品牌包包女包斜挎单肩小包女式','','534,289,290,300,302',116.00,0.00,156.00,0.00,'件',0,0,289,1,1,1,1,1,1640408296,0,0,0,0,26.00,0,NULL,0,0,86,1,'','','',1,0,'0,1,2,3','',0),(5,0,'xlwebimage/public/maintain/2021/12/25/f65ef5469f3a479497a710fb288d115cvl5imkvpo8.jpg','[\"xlwebimage/public/maintain/2021/12/25/f65ef5469f3a479497a710fb288d115cvl5imkvpo8.jpg\"]','LAORENTOU包包女包牛皮单肩女士包包奢侈品蓝色【礼盒装】','LAORENTOU包包女包牛皮单肩女士包包奢侈品蓝色【礼盒装】','LAORENTOU包包女包牛皮单肩女士包包奢侈品蓝色【礼盒装】','','246,248,258,488,288,289,300,302',115.00,0.00,145.00,0.00,'件',0,1,998,1,1,1,1,1,1640408403,0,0,0,0,15.00,0,NULL,0,0,99,0,'','','',1,0,'0,1,2,3','',0),(6,0,'xlwebimage/public/maintain/2021/12/25/fef1aee22a04466d93693f5791d44bfd2rsm469mcr.jpg','[\"xlwebimage/public/maintain/2021/12/25/fef1aee22a04466d93693f5791d44bfd2rsm469mcr.jpg\"]','第一江南 西湖龙井茶叶礼盒 明前特级龙井绿茶【至臻献礼200g】礼盒装','第一江南 西湖龙井茶叶礼盒 明前特级龙井绿茶【至臻献礼200g】礼盒装','第一江南 西湖龙井茶叶礼盒 明前特级龙井绿茶【至臻献礼200g】礼盒装','','246,248,258,488',1588.00,0.00,1688.00,0.00,'件',0,0,999,1,1,1,1,1,1640408500,0,0,0,0,300.00,0,NULL,0,0,666,15,'','','',1,0,'0,1,2,3','',0),(7,0,'xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg','[\"xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg\"]','车载小鹿首饰架摆件鹿角树形耳钉首饰展示架耳环架手链饰品收纳首饰挂件 白色','车载小鹿首饰架摆件鹿角树形耳钉首饰展示架耳环架手链饰品收纳首饰挂件 白色','车载小鹿首饰架摆件鹿角树形耳钉首饰展示架耳环架手链饰品收纳首饰挂件 白色','','534,290,300,302',13.00,0.00,18.00,0.00,'个',0,0,1533,1,1,1,1,1,1640408617,0,0,0,0,5.00,0,NULL,0,0,46,22,'','','',1,1,'0,1,2,3','',0),(8,0,'xlwebimage/public/maintain/2021/12/25/7a6475bba07c47bb98e5db0e0d21c8ebq5sw2kijlc.jpg','[\"xlwebimage/public/maintain/2021/12/25/7a6475bba07c47bb98e5db0e0d21c8ebq5sw2kijlc.jpg\"]','兰蔻（LANCOME）口红196 全新菁纯丝绒雾面唇膏 化妆品礼盒','兰蔻（LANCOME）口红196 全新菁纯丝绒雾面唇膏 化妆品礼盒','兰蔻（LANCOME）口红196 全新菁纯丝绒雾面唇膏 化妆品礼盒','','246,248,258,488,291,292,293,294,295,296',787.00,0.00,197.00,0.00,'件',0,0,689,1,1,1,1,1,1640408735,0,0,0,0,212.00,0,NULL,0,0,66,3,'','','',1,0,'0,1,2,3','',0);
/*!40000 ALTER TABLE `eb_store_product` ENABLE KEYS */;


--
-- Table structure for table `eb_store_product_attr`
--

DROP TABLE IF EXISTS `eb_store_product_attr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_product_attr` (
                                         `id` mediumint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                         `product_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商品ID',
                                         `attr_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性名',
                                         `attr_values` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性值',
                                         `type` tinyint(1) DEFAULT '0' COMMENT '活动类型 0=商品，1=秒杀，2=砍价，3=拼团',
                                         `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0-否，1-是',
                                         PRIMARY KEY (`id`) USING BTREE,
                                         KEY `store_id` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='商品属性表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_product_attr`
--


/*!40000 ALTER TABLE `eb_store_product_attr` DISABLE KEYS */;
INSERT INTO `eb_store_product_attr` VALUES (1,1,'规格','默认',0,0),(2,2,'规格','默认',0,0),(3,3,'规格','默认',0,1),(4,4,'规格','默认',0,0),(5,5,'规格','默认',0,0),(6,6,'规格','默认',0,0),(7,7,'规格','默认',0,1),(8,8,'规格','默认',0,0),(9,1,'规格','默认',1,0),(10,2,'规格','默认',1,0),(11,1,'规格','默认',2,0),(12,2,'规格','默认',2,0),(13,1,'规格','默认',3,0),(14,2,'规格','默认',3,0),(15,7,'颜色','白色,黑色,粉色,红色',0,0),(16,3,'颜色','格物致知蓝咖,格物致知红色',0,0),(17,3,'尺码','200*27cm,240*30',0,0),(18,3,'颜色','格物致知蓝咖,格物致知红色',1,0),(19,3,'尺码','200*27cm,240*30',1,0),(20,4,'颜色','格物致知蓝咖,格物致知红色',1,0),(21,4,'尺码','200*27cm,240*30',1,0),(22,3,'颜色','白色,黑色,粉色,红色',3,0),(23,4,'颜色','格物致知蓝咖,格物致知红色',3,0),(24,4,'尺码','200*27cm,240*30',3,0);
/*!40000 ALTER TABLE `eb_store_product_attr` ENABLE KEYS */;


--
-- Table structure for table `eb_store_product_attr_result`
--

DROP TABLE IF EXISTS `eb_store_product_attr_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_product_attr_result` (
                                                `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                `product_id` int(10) unsigned NOT NULL COMMENT '商品ID',
                                                `result` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品属性参数',
                                                `change_time` int(10) unsigned NOT NULL COMMENT '上次修改时间',
                                                `type` tinyint(1) DEFAULT '0' COMMENT '活动类型 0=商品，1=秒杀，2=砍价，3=拼团',
                                                PRIMARY KEY (`id`) USING BTREE,
                                                KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='商品属性详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_product_attr_result`
--


/*!40000 ALTER TABLE `eb_store_product_attr_result` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_product_attr_result` ENABLE KEYS */;


--
-- Table structure for table `eb_store_product_attr_value`
--

DROP TABLE IF EXISTS `eb_store_product_attr_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_product_attr_value` (
                                               `id` mediumint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                               `product_id` int(10) unsigned NOT NULL COMMENT '商品ID',
                                               `suk` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品属性索引值 (attr_value|attr_value[|....])',
                                               `stock` int(10) unsigned NOT NULL COMMENT '属性对应的库存',
                                               `sales` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '销量',
                                               `price` decimal(8,2) unsigned NOT NULL COMMENT '属性金额',
                                               `image` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图片',
                                               `unique` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '唯一值',
                                               `cost` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '成本价',
                                               `bar_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '商品条码',
                                               `ot_price` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '原价',
                                               `weight` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '重量',
                                               `volume` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '体积',
                                               `brokerage` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '一级返佣',
                                               `brokerage_two` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '二级返佣',
                                               `type` tinyint(1) DEFAULT '0' COMMENT '活动类型 0=商品，1=秒杀，2=砍价，3=拼团',
                                               `quota` int(11) DEFAULT NULL COMMENT '活动限购数量',
                                               `quota_show` int(11) DEFAULT NULL COMMENT '活动限购数量显示',
                                               `attr_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT 'attr_values 创建更新时的属性对应',
                                               `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0-否，1-是',
                                               PRIMARY KEY (`id`) USING BTREE,
                                               KEY `unique` (`unique`,`suk`) USING BTREE,
                                               KEY `store_id` (`product_id`,`suk`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='商品属性值表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_product_attr_value`
--


/*!40000 ALTER TABLE `eb_store_product_attr_value` DISABLE KEYS */;
INSERT INTO `eb_store_product_attr_value` VALUES (1,1,'默认',345,0,268.00,'xlwebimage/public/maintain/2021/12/25/63969148b6c4447d918124fd810c1da5m2h9aiylly.jpg','',40.00,'',188.00,1.00,0.00,0.00,0.00,0,0,0,'{\"规格\":\"默认\"}',0),(2,2,'默认',88,0,99.00,'xlwebimage/public/maintain/2021/12/25/49ae68efce4848a185a8ddcb4e5c95a2zl2dlwi77v.jpg','',20.00,'',129.00,2.00,0.00,0.00,0.00,0,0,0,'{\"规格\":\"默认\"}',0),(3,3,'默认',78,0,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',89.00,'',394.00,0.50,0.00,0.00,0.00,0,0,0,'{\"规格\":\"默认\"}',1),(4,4,'默认',289,0,116.00,'xlwebimage/public/maintain/2021/12/25/3ed152917e3f4f7faa9414050b6cab49vuadulntb8.jpg','',26.00,'',156.00,1.00,0.00,0.00,0.00,0,0,0,'{\"规格\":\"默认\"}',0),(5,5,'默认',998,1,115.00,'xlwebimage/public/maintain/2021/12/25/f65ef5469f3a479497a710fb288d115cvl5imkvpo8.jpg','',15.00,'',145.00,0.50,0.00,0.00,0.00,0,0,0,'{\"规格\":\"默认\"}',0),(6,6,'默认',999,0,1588.00,'xlwebimage/public/maintain/2021/12/25/fef1aee22a04466d93693f5791d44bfd2rsm469mcr.jpg','',300.00,'',1688.00,2.00,0.00,0.00,0.00,0,0,0,'{\"规格\":\"默认\"}',0),(7,7,'默认',666,0,13.00,'xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg','',5.00,'',18.00,0.50,0.00,0.00,0.00,0,0,0,'{\"规格\":\"默认\"}',1),(8,8,'默认',689,0,787.00,'xlwebimage/public/maintain/2021/12/25/7a6475bba07c47bb98e5db0e0d21c8ebq5sw2kijlc.jpg','',212.00,'',197.00,0.50,0.00,0.00,0.00,0,0,0,'{\"规格\":\"默认\"}',0),(9,1,'默认',689,0,720.00,'xlwebimage/public/maintain/2021/12/25/7a6475bba07c47bb98e5db0e0d21c8ebq5sw2kijlc.jpg','',212.00,'',197.00,0.50,0.00,0.00,0.00,1,99,99,'\"\\\"{\\\\\\\"规格\\\\\\\":\\\\\\\"默认\\\\\\\"}\\\"\"',0),(10,2,'默认',998,1,95.00,'xlwebimage/public/maintain/2021/12/25/f65ef5469f3a479497a710fb288d115cvl5imkvpo8.jpg','',15.00,'',145.00,0.50,0.00,0.00,0.00,1,98,99,'\"\\\"{\\\\\\\"规格\\\\\\\":\\\\\\\"默认\\\\\\\"}\\\"\"',0),(11,1,'默认',78,0,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',89.00,'',394.00,0.50,0.00,0.00,0.00,2,66,0,'{\"规格\":\"默认\"}',0),(12,2,'默认',345,0,268.00,'xlwebimage/public/maintain/2021/12/25/63969148b6c4447d918124fd810c1da5m2h9aiylly.jpg','',40.00,'',188.00,1.00,0.00,0.00,0.00,2,69,0,'{\"规格\":\"默认\"}',0),(13,1,'默认',666,0,13.00,'xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg','',5.00,'',18.00,0.50,0.00,0.00,0.00,3,66,66,'\"{\\\"规格\\\":\\\"默认\\\"}\"',0),(14,2,'默认',999,0,1288.00,'xlwebimage/public/maintain/2021/12/25/fef1aee22a04466d93693f5791d44bfd2rsm469mcr.jpg','',300.00,'',1688.00,2.00,0.00,0.00,0.00,3,99,99,'\"{\\\"规格\\\":\\\"默认\\\"}\"',0),(15,7,'白色',365,0,13.00,'xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg','',5.00,'',18.00,1.00,0.00,0.00,0.00,0,0,0,'{\"颜色\":\"白色\"}',0),(16,7,'黑色',211,0,13.00,'xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg','',5.00,'',18.00,1.00,0.00,0.00,0.00,0,0,0,'{\"颜色\":\"黑色\"}',0),(17,7,'粉色',568,0,13.00,'xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg','',5.00,'',18.00,1.00,0.00,0.00,0.00,0,0,0,'{\"颜色\":\"粉色\"}',0),(18,7,'红色',389,0,13.00,'xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg','',5.00,'',18.00,1.00,0.00,0.00,0.00,0,0,0,'{\"颜色\":\"红色\"}',0),(19,3,'格物致知蓝咖,200*27cm',698,0,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,0,0,0,'{\"颜色\":\"格物致知蓝咖\",\"尺码\":\"200*27cm\"}',0),(20,3,'格物致知蓝咖,240*30',366,0,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,0,0,0,'{\"颜色\":\"格物致知蓝咖\",\"尺码\":\"240*30\"}',0),(21,3,'格物致知红色,200*27cm',124,0,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,0,0,0,'{\"颜色\":\"格物致知红色\",\"尺码\":\"200*27cm\"}',0),(22,3,'格物致知红色,240*30',677,1,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,0,0,0,'{\"颜色\":\"格物致知红色\",\"尺码\":\"240*30\"}',0),(23,3,'格物致知红色,200*27cm',124,0,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,1,124,124,'{\"颜色\":\"格物致知红色\",\"尺码\":\"200*27cm\"}',0),(24,3,'格物致知红色,240*30',678,0,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,1,678,678,'{\"颜色\":\"格物致知红色\",\"尺码\":\"240*30\"}',0),(25,3,'格物致知蓝咖,200*27cm',698,0,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,1,698,698,'{\"颜色\":\"格物致知蓝咖\",\"尺码\":\"200*27cm\"}',0),(26,3,'格物致知蓝咖,240*30',366,0,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,1,366,366,'{\"颜色\":\"格物致知蓝咖\",\"尺码\":\"240*30\"}',0),(27,4,'格物致知红色,200*27cm',124,0,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,1,124,124,'\"{\\\"颜色\\\":\\\"格物致知红色\\\",\\\"尺码\\\":\\\"200*27cm\\\"}\"',0),(28,4,'格物致知红色,240*30',678,0,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,1,678,678,'\"{\\\"颜色\\\":\\\"格物致知红色\\\",\\\"尺码\\\":\\\"240*30\\\"}\"',0),(29,4,'格物致知蓝咖,200*27cm',698,0,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,1,698,698,'\"{\\\"颜色\\\":\\\"格物致知蓝咖\\\",\\\"尺码\\\":\\\"200*27cm\\\"}\"',0),(30,4,'格物致知蓝咖,240*30',366,0,374.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,1,366,366,'\"{\\\"颜色\\\":\\\"格物致知蓝咖\\\",\\\"尺码\\\":\\\"240*30\\\"}\"',0),(31,3,'白色',365,0,8.00,'xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg','',5.00,'',18.00,1.00,0.00,0.00,0.00,3,36,36,'{\"颜色\":\"白色\"}',0),(32,3,'粉色',568,0,8.00,'xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg','',5.00,'',18.00,1.00,0.00,0.00,0.00,3,56,56,'{\"颜色\":\"粉色\"}',0),(33,3,'红色',389,0,8.00,'xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg','',5.00,'',18.00,1.00,0.00,0.00,0.00,3,38,38,'{\"颜色\":\"红色\"}',0),(34,3,'黑色',211,0,8.00,'xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg','',5.00,'',18.00,1.00,0.00,0.00,0.00,3,21,21,'{\"颜色\":\"黑色\"}',0),(35,4,'格物致知红色,200*27cm',124,0,324.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,3,12,12,'{\"颜色\":\"格物致知红色\",\"尺码\":\"200*27cm\"}',0),(36,4,'格物致知红色,240*30',677,1,324.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,3,66,67,'{\"颜色\":\"格物致知红色\",\"尺码\":\"240*30\"}',0),(37,4,'格物致知蓝咖,200*27cm',698,0,324.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,3,69,69,'{\"颜色\":\"格物致知蓝咖\",\"尺码\":\"200*27cm\"}',0),(38,4,'格物致知蓝咖,240*30',366,0,324.00,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','',100.00,'',412.00,0.50,0.00,0.00,0.00,3,36,36,'{\"颜色\":\"格物致知蓝咖\",\"尺码\":\"240*30\"}',0);
/*!40000 ALTER TABLE `eb_store_product_attr_value` ENABLE KEYS */;


--
-- Table structure for table `eb_store_product_cate`
--

DROP TABLE IF EXISTS `eb_store_product_cate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_product_cate` (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `product_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品id',
                                         `cate_id` int(11) NOT NULL DEFAULT '0' COMMENT '分类id',
                                         `add_time` int(11) NOT NULL DEFAULT '0' COMMENT '添加时间',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='商品分类辅助表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_product_cate`
--


/*!40000 ALTER TABLE `eb_store_product_cate` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_product_cate` ENABLE KEYS */;


--
-- Table structure for table `eb_store_product_coupon`
--

DROP TABLE IF EXISTS `eb_store_product_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_product_coupon` (
                                           `id` int(10) NOT NULL AUTO_INCREMENT,
                                           `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品id',
                                           `issue_coupon_id` int(10) NOT NULL DEFAULT '0' COMMENT '优惠劵id',
                                           `add_time` int(10) NOT NULL DEFAULT '0' COMMENT '添加时间',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='商品优惠券表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_product_coupon`
--


/*!40000 ALTER TABLE `eb_store_product_coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_product_coupon` ENABLE KEYS */;


--
-- Table structure for table `eb_store_product_description`
--

DROP TABLE IF EXISTS `eb_store_product_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_product_description` (
                                                `product_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品ID',
                                                `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品详情',
                                                `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '商品类型',
                                                `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                                                PRIMARY KEY (`id`) USING BTREE,
                                                KEY `product_id` (`product_id`,`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='商品描述表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_product_description`
--


/*!40000 ALTER TABLE `eb_store_product_description` DISABLE KEYS */;
INSERT INTO `eb_store_product_description` VALUES (1,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/63969148b6c4447d918124fd810c1da5m2h9aiylly.jpg\" /></p>',0,1),(2,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/49ae68efce4848a185a8ddcb4e5c95a2zl2dlwi77v.jpg\" /></p>',0,2),(4,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/3ed152917e3f4f7faa9414050b6cab49vuadulntb8.jpg\" /></p>',0,4),(5,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/f65ef5469f3a479497a710fb288d115cvl5imkvpo8.jpg\" /></p>',0,5),(6,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/fef1aee22a04466d93693f5791d44bfd2rsm469mcr.jpg\" /></p>',0,6),(8,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/7a6475bba07c47bb98e5db0e0d21c8ebq5sw2kijlc.jpg\" /></p>',0,8),(1,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/7a6475bba07c47bb98e5db0e0d21c8ebq5sw2kijlc.jpg\" /></p>',1,10),(2,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/f65ef5469f3a479497a710fb288d115cvl5imkvpo8.jpg\" /></p>',1,12),(1,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg\" /></p>',2,14),(2,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/63969148b6c4447d918124fd810c1da5m2h9aiylly.jpg\" /></p>',2,15),(1,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg\" /></p>',3,16),(2,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/fef1aee22a04466d93693f5791d44bfd2rsm469mcr.jpg\" /></p>',3,17),(7,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg\" /></p>',0,18),(3,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg\" /></p>',0,19),(3,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg\" /></p>',1,20),(4,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg\" /></p>',1,21),(3,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/37382cca048b4011bdbacd3cbbe0814daw1o5x0tco.jpg\" /></p>',3,22),(4,'<p><img class=\"wscnph\" src=\"xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg\" /></p>',3,23);
/*!40000 ALTER TABLE `eb_store_product_description` ENABLE KEYS */;


--
-- Table structure for table `eb_store_product_log`
--

DROP TABLE IF EXISTS `eb_store_product_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_product_log` (
                                        `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '统计ID',
                                        `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型visit,cart,order,pay,collect,refund',
                                        `product_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品ID',
                                        `uid` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
                                        `visit_num` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否浏览',
                                        `cart_num` int(11) NOT NULL DEFAULT '0' COMMENT '加入购物车数量',
                                        `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '下单数量',
                                        `pay_num` int(11) NOT NULL DEFAULT '0' COMMENT '支付数量',
                                        `pay_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
                                        `cost_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品成本价',
                                        `pay_uid` int(11) NOT NULL DEFAULT '0' COMMENT '支付用户ID',
                                        `refund_num` int(11) NOT NULL DEFAULT '0' COMMENT '退款数量',
                                        `refund_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '退款金额',
                                        `collect_num` tinyint(1) NOT NULL DEFAULT '0' COMMENT '收藏',
                                        `add_time` bigint(14) NOT NULL DEFAULT '0' COMMENT '添加时间',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='商品日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_product_log`
--


/*!40000 ALTER TABLE `eb_store_product_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_product_log` ENABLE KEYS */;


--
-- Table structure for table `eb_store_product_relation`
--

DROP TABLE IF EXISTS `eb_store_product_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_product_relation` (
                                             `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                                             `uid` int(10) unsigned NOT NULL COMMENT '用户ID',
                                             `product_id` int(10) unsigned NOT NULL COMMENT '商品ID',
                                             `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型(收藏(collect）、点赞(like))',
                                             `category` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '某种类型的商品(普通商品、秒杀商品)',
                                             `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                             `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                             PRIMARY KEY (`id`) USING BTREE,
                                             UNIQUE KEY `uid` (`uid`,`product_id`,`type`,`category`) USING BTREE,
                                             KEY `type` (`type`) USING BTREE,
                                             KEY `category` (`category`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='商品点赞和收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_product_relation`
--


/*!40000 ALTER TABLE `eb_store_product_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_product_relation` ENABLE KEYS */;


--
-- Table structure for table `eb_store_product_reply`
--

DROP TABLE IF EXISTS `eb_store_product_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_product_reply` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
                                          `uid` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
                                          `oid` int(11) NOT NULL DEFAULT '0' COMMENT '订单ID',
                                          `unique` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品唯一id',
                                          `product_id` int(11) NOT NULL COMMENT '商品id',
                                          `reply_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'product' COMMENT '某种商品类型(普通商品、秒杀商品）',
                                          `product_score` tinyint(1) NOT NULL COMMENT '商品分数',
                                          `service_score` tinyint(1) NOT NULL COMMENT '服务分数',
                                          `comment` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
                                          `pics` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论图片',
                                          `merchant_reply_content` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '管理员回复内容',
                                          `merchant_reply_time` int(11) DEFAULT NULL COMMENT '管理员回复时间',
                                          `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0未删除1已删除',
                                          `is_reply` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未回复1已回复',
                                          `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
                                          `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户头像',
                                          `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          `sku` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '商品规格属性值,多个,号隔开',
                                          PRIMARY KEY (`id`) USING BTREE,
                                          KEY `parent_id` (`reply_type`) USING BTREE,
                                          KEY `is_del` (`is_del`) USING BTREE,
                                          KEY `product_score` (`product_score`) USING BTREE,
                                          KEY `service_score` (`service_score`) USING BTREE,
                                          KEY `uid+oid` (`uid`,`oid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_product_reply`
--


/*!40000 ALTER TABLE `eb_store_product_reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_store_product_reply` ENABLE KEYS */;


--
-- Table structure for table `eb_store_product_rule`
--

DROP TABLE IF EXISTS `eb_store_product_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_product_rule` (
                                         `id` int(10) NOT NULL AUTO_INCREMENT,
                                         `rule_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规格名称',
                                         `rule_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规格值',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='商品规则值(规格)表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_product_rule`
--


/*!40000 ALTER TABLE `eb_store_product_rule` DISABLE KEYS */;
INSERT INTO `eb_store_product_rule` VALUES (1,'颜色','[{\"value\":\"颜色\",\"detail\":[\"白色\",\"黑色\",\"粉色\",\"红色\"],\"inputVisible\":false}]'),(2,'围巾','[{\"value\":\"颜色\",\"detail\":[\"格物致知蓝咖\",\"格物致知红色\"],\"inputVisible\":false},{\"value\":\"尺码\",\"detail\":[\"200*27cm\",\"240*30\"],\"inputVisible\":false}]');
/*!40000 ALTER TABLE `eb_store_product_rule` ENABLE KEYS */;


--
-- Table structure for table `eb_store_seckill`
--

DROP TABLE IF EXISTS `eb_store_seckill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_seckill` (
                                    `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品秒杀产品表id',
                                    `product_id` int(10) unsigned NOT NULL COMMENT '商品id',
                                    `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '推荐图',
                                    `images` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '轮播图',
                                    `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动标题',
                                    `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '简介',
                                    `price` decimal(10,2) unsigned NOT NULL COMMENT '价格',
                                    `cost` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '成本',
                                    `ot_price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '原价',
                                    `give_integral` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '返多少积分',
                                    `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
                                    `stock` int(10) unsigned NOT NULL COMMENT '库存',
                                    `sales` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '销量',
                                    `unit_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单位名',
                                    `postage` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '邮费',
                                    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '内容',
                                    `start_time` datetime NOT NULL COMMENT '开始时间',
                                    `stop_time` datetime NOT NULL COMMENT '结束时间',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                                    `status` tinyint(3) unsigned NOT NULL COMMENT '秒杀状态 0=关闭 1=开启',
                                    `is_postage` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否包邮',
                                    `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '删除 0未删除1已删除',
                                    `num` int(11) unsigned NOT NULL COMMENT '当天参与活动次数',
                                    `is_show` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '显示',
                                    `time_id` int(11) DEFAULT NULL COMMENT '时间段ID',
                                    `temp_id` int(11) NOT NULL DEFAULT '0' COMMENT '运费模板ID',
                                    `weight` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '重量',
                                    `volume` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '体积',
                                    `quota` int(10) NOT NULL DEFAULT '0' COMMENT '限购总数,随减',
                                    `quota_show` int(10) NOT NULL DEFAULT '0' COMMENT '限购总数显示.不变',
                                    `spec_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '规格 0=单 1=多',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    KEY `product_id` (`product_id`) USING BTREE,
                                    KEY `start_time` (`start_time`,`stop_time`) USING BTREE,
                                    KEY `is_del` (`is_del`) USING BTREE,
                                    KEY `is_show` (`status`) USING BTREE,
                                    KEY `add_time` (`create_time`) USING BTREE,
                                    KEY `sort` (`sort`) USING BTREE,
                                    KEY `is_postage` (`is_postage`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='商品秒杀产品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_seckill`
--


/*!40000 ALTER TABLE `eb_store_seckill` DISABLE KEYS */;
INSERT INTO `eb_store_seckill` VALUES (1,8,'xlwebimage/public/maintain/2021/12/25/7a6475bba07c47bb98e5db0e0d21c8ebq5sw2kijlc.jpg','[\"xlwebimage/public/maintain/2021/12/25/7a6475bba07c47bb98e5db0e0d21c8ebq5sw2kijlc.jpg\"]','兰蔻（LANCOME）口红196 全新菁纯丝绒雾面唇膏 化妆品礼盒','兰蔻（LANCOME）口红196 全新菁纯丝绒雾面唇膏 化妆品礼盒',720.00,212.00,197.00,0.00,0,99,0,'件',0.00,NULL,'2021-12-25 00:00:00','2022-02-28 00:00:00','2021-12-25 13:08:50',1,0,0,1,1,2,1,0.00,0.00,99,99,0),(2,5,'xlwebimage/public/maintain/2021/12/25/f65ef5469f3a479497a710fb288d115cvl5imkvpo8.jpg','[\"xlwebimage/public/maintain/2021/12/25/f65ef5469f3a479497a710fb288d115cvl5imkvpo8.jpg\"]','LAORENTOU包包女包牛皮单肩女士包包奢侈品蓝色【礼盒装】','LAORENTOU包包女包牛皮单肩女士包包奢侈品蓝色【礼盒装】',95.00,15.00,145.00,0.00,0,98,1,'件',0.00,NULL,'2021-12-25 00:00:00','2022-02-28 00:00:00','2021-12-25 13:10:53',1,0,0,1,1,2,1,0.00,0.00,98,99,0),(3,3,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','[\"xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg\"]','万事利秋冬新品时尚百搭经典真丝绒厚款围巾 格物致知蓝咖','万事利秋冬新品时尚百搭经典真丝绒厚款围巾 格物致知蓝咖',374.00,100.00,412.00,0.00,0,1866,0,'件',0.00,NULL,'2021-12-29 00:00:00','2022-01-31 00:00:00','2021-12-29 11:46:20',1,0,0,1,1,1,1,0.00,0.00,1866,1866,1),(4,3,'xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg','[\"xlwebimage/public/maintain/2021/12/25/5486775d8cad4fd8adfc254d2e93857cn0wu5hq8f4.jpg\"]','万事利秋冬新品时尚百搭经典真丝绒厚款围巾','万事利秋冬新品时尚百搭经典真丝绒厚款围巾 格物致知蓝咖',374.00,100.00,412.00,0.00,0,1866,0,'件',0.00,NULL,'2021-12-29 00:00:00','2022-01-31 00:00:00','2021-12-29 11:47:03',1,0,0,1,1,2,1,0.00,0.00,1866,1866,1);
/*!40000 ALTER TABLE `eb_store_seckill` ENABLE KEYS */;


--
-- Table structure for table `eb_store_seckill_manger`
--

DROP TABLE IF EXISTS `eb_store_seckill_manger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_store_seckill_manger` (
                                           `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                                           `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '秒杀名称',
                                           `start_time` int(11) DEFAULT NULL COMMENT '秒杀开始时间段',
                                           `end_time` int(11) DEFAULT NULL COMMENT '秒杀结束时间段',
                                           `img` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '主图',
                                           `silder_imgs` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '轮播图',
                                           `sort` int(11) DEFAULT NULL COMMENT '排序',
                                           `status` int(11) DEFAULT NULL COMMENT '状态 0=关闭 1=开启',
                                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                           `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                           `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '删除标记 0=为删除 1=删除',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_store_seckill_manger`
--


/*!40000 ALTER TABLE `eb_store_seckill_manger` DISABLE KEYS */;
INSERT INTO `eb_store_seckill_manger` VALUES (1,'白领',10,13,NULL,'[{\"attId\":192,\"name\":\"eab9cdc74c4e4673acab70b801ee4612wnxgu98i0l.jpg\",\"attDir\":\"\",\"sattDir\":\"xlwebimage/public/maintain/2021/12/25/e91ccd7e37294221bfdb7b43a42d8ed5hghg7nw0qb.jpg\",\"attSize\":\"118260\",\"attType\":\"jpeg\",\"pid\":0,\"imageType\":1,\"createTime\":\"2021-12-25 11:57:38\",\"updateTime\":\"2021-12-25 11:57:38\",\"isSelect\":true,\"num\":1}]',NULL,1,'2021-06-03 10:48:58','2021-12-25 13:07:21',0),(2,'夜猫子',13,18,NULL,'[{\"attId\":191,\"name\":\"2702ff9ddd4e40ee97c957f07e9417b10hrby3rl2i.jpg\",\"attDir\":\"\",\"sattDir\":\"xlwebimage/public/maintain/2021/12/25/9e97ddf78f804d50b985bdcf666478ebj76hbf3gpp.jpg\",\"attSize\":\"117394\",\"attType\":\"jpeg\",\"pid\":0,\"imageType\":1,\"createTime\":\"2021-12-25 11:57:38\",\"updateTime\":\"2021-12-25 11:57:38\",\"isSelect\":true,\"num\":2}]',NULL,1,'2021-06-03 14:12:45','2021-12-29 13:58:14',0),(4,'下班拉',18,22,NULL,'[{\"attId\":193,\"name\":\"0638dad477de4f979f1d6f58aa960c41bvp53cy53v.jpg\",\"attDir\":\"\",\"sattDir\":\"xlwebimage/public/maintain/2021/12/25/3598e8d0aa3f4bf1b61140daa0e36300k0te4s6z3j.jpg\",\"attSize\":\"149827\",\"attType\":\"jpeg\",\"pid\":0,\"imageType\":1,\"createTime\":\"2021-12-25 11:57:38\",\"updateTime\":\"2021-12-25 11:57:38\",\"localImg\":\"\",\"isSelect\":true,\"num\":1}]',NULL,1,'2021-07-17 17:46:35','2021-12-25 13:07:11',0);
/*!40000 ALTER TABLE `eb_store_seckill_manger` ENABLE KEYS */;



