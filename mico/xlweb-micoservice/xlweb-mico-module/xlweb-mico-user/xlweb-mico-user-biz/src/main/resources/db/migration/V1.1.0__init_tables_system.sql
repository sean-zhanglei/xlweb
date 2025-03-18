


--
-- Table structure for table `eb_user`
--

DROP TABLE IF EXISTS `eb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user` (
                           `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
                           `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户账号',
                           `pwd` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户密码',
                           `real_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '真实姓名',
                           `birthday` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '生日',
                           `card_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '身份证号码',
                           `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户备注',
                           `partner_id` int(11) DEFAULT NULL COMMENT '合伙人id',
                           `group_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户分组id',
                           `tag_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '标签id',
                           `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户昵称',
                           `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户头像',
                           `phone` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号码',
                           `add_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '添加ip',
                           `last_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '最后一次登录ip',
                           `now_money` decimal(16,2) unsigned DEFAULT '0.00' COMMENT '用户余额',
                           `brokerage_price` decimal(8,2) DEFAULT '0.00' COMMENT '佣金金额',
                           `integral` int(11) DEFAULT '0' COMMENT '用户剩余积分',
                           `experience` int(11) unsigned DEFAULT '0' COMMENT '用户剩余经验',
                           `sign_num` int(11) DEFAULT '0' COMMENT '连续签到天数',
                           `status` tinyint(1) DEFAULT '1' COMMENT '1为正常，0为禁止',
                           `level` tinyint(2) unsigned DEFAULT '0' COMMENT '等级',
                           `spread_uid` int(10) unsigned DEFAULT '0' COMMENT '推广员id',
                           `spread_time` timestamp NULL DEFAULT NULL COMMENT '推广员关联时间',
                           `user_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户类型',
                           `is_promoter` tinyint(1) unsigned DEFAULT '0' COMMENT '是否为推广员',
                           `pay_count` int(11) unsigned DEFAULT '0' COMMENT '用户购买次数',
                           `spread_count` int(11) DEFAULT '0' COMMENT '下级人数',
                           `addres` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '详细地址',
                           `adminid` int(11) unsigned DEFAULT '0' COMMENT '管理员编号 ',
                           `login_type` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户登陆类型，h5,wechat,routine',
                           `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后一次登录时间',
                           `clean_time` timestamp NULL DEFAULT NULL COMMENT '清除时间',
                           `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '/0/' COMMENT '推广等级记录',
                           `subscribe` tinyint(3) DEFAULT '0' COMMENT '是否关注公众号',
                           `subscribe_time` timestamp NULL DEFAULT NULL COMMENT '关注公众号时间',
                           `sex` tinyint(1) DEFAULT '1' COMMENT '性别，0未知，1男，2女，3保密',
                           `country` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'CN' COMMENT '国家，中国CN，其他OTHER',
                           `promoter_time` timestamp NULL DEFAULT NULL COMMENT '成为分销员时间',
                           PRIMARY KEY (`uid`) USING BTREE,
                           UNIQUE KEY `account` (`account`) USING BTREE,
                           KEY `spreaduid` (`spread_uid`) USING BTREE,
                           KEY `level` (`level`) USING BTREE,
                           KEY `status` (`status`) USING BTREE,
                           KEY `is_promoter` (`is_promoter`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user`
--


/*!40000 ALTER TABLE `eb_user` DISABLE KEYS */;
INSERT INTO `eb_user` VALUES (1,'18292417675','f6mcpGQ8NEmwbab2TlkpUg==','','','','',NULL,'','','大粽子','xlwebimage/public/maintain/2023/02/23/1c0fa967eb764d918f064744cc51dc70a6f2bj3sha.jpg','18292417675','','',0.00,0.00,0,0,0,1,0,0,NULL,'',0,0,0,'',0,'','2023-03-15 09:41:12','2023-03-15 09:48:11','2023-03-15 09:42:44',NULL,'/0/',0,NULL,1,'CN',NULL);
/*!40000 ALTER TABLE `eb_user` ENABLE KEYS */;


--
-- Table structure for table `eb_user_address`
--

DROP TABLE IF EXISTS `eb_user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user_address` (
                                   `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户地址id',
                                   `uid` int(10) unsigned NOT NULL COMMENT '用户id',
                                   `real_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '收货人姓名',
                                   `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '收货人电话',
                                   `province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '收货人所在省',
                                   `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '收货人所在市',
                                   `city_id` int(11) NOT NULL DEFAULT '0' COMMENT '城市id',
                                   `district` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '收货人所在区',
                                   `detail` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '收货人详细地址',
                                   `post_code` int(10) NOT NULL DEFAULT '0' COMMENT '邮编',
                                   `longitude` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '经度',
                                   `latitude` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '纬度',
                                   `is_default` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否默认',
                                   `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
                                   `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   KEY `uid` (`uid`) USING BTREE,
                                   KEY `is_default` (`is_default`) USING BTREE,
                                   KEY `is_del` (`is_del`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户地址表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user_address`
--


/*!40000 ALTER TABLE `eb_user_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_user_address` ENABLE KEYS */;


--
-- Table structure for table `eb_user_bill`
--

DROP TABLE IF EXISTS `eb_user_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user_bill` (
                                `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户账单id',
                                `uid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户uid',
                                `link_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '关联id',
                                `pm` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0 = 支出 1 = 获得',
                                `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账单标题',
                                `category` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '明细种类',
                                `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '明细类型',
                                `number` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '明细数字',
                                `balance` decimal(16,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '剩余',
                                `mark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                                `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0 = 带确定 1 = 有效 -1 = 无效',
                                `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                                `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                KEY `openid` (`uid`) USING BTREE,
                                KEY `status` (`status`) USING BTREE,
                                KEY `add_time` (`create_time`) USING BTREE,
                                KEY `pm` (`pm`) USING BTREE,
                                KEY `type` (`category`,`type`,`link_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户账单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user_bill`
--


/*!40000 ALTER TABLE `eb_user_bill` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_user_bill` ENABLE KEYS */;


--
-- Table structure for table `eb_user_brokerage_record`
--

DROP TABLE IF EXISTS `eb_user_brokerage_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user_brokerage_record` (
                                            `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '记录id',
                                            `uid` int(10) NOT NULL DEFAULT '0' COMMENT '用户uid',
                                            `link_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '关联id（orderNo,提现id）',
                                            `link_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '关联类型（order,extract，yue）',
                                            `type` int(1) NOT NULL DEFAULT '1' COMMENT '类型：1-增加，2-扣减（提现）',
                                            `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
                                            `price` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
                                            `balance` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '剩余',
                                            `mark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                                            `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款），5-提现申请',
                                            `frozen_time` int(3) NOT NULL DEFAULT '0' COMMENT '冻结期时间（天）',
                                            `thaw_time` bigint(14) NOT NULL DEFAULT '0' COMMENT '解冻时间',
                                            `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                                            `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                            `brokerage_level` int(2) DEFAULT NULL COMMENT '分销等级',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            KEY `openid` (`uid`) USING BTREE,
                                            KEY `status` (`status`) USING BTREE,
                                            KEY `add_time` (`create_time`) USING BTREE,
                                            KEY `type` (`type`) USING BTREE,
                                            KEY `type_link` (`type`,`link_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户佣金记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user_brokerage_record`
--


/*!40000 ALTER TABLE `eb_user_brokerage_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_user_brokerage_record` ENABLE KEYS */;


--
-- Table structure for table `eb_user_experience_record`
--

DROP TABLE IF EXISTS `eb_user_experience_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user_experience_record` (
                                             `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '记录id',
                                             `uid` int(10) NOT NULL DEFAULT '0' COMMENT '用户uid',
                                             `link_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '关联id-orderNo,(sign,system默认为0）',
                                             `link_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'order' COMMENT '关联类型（order,sign,system）',
                                             `type` int(1) NOT NULL DEFAULT '1' COMMENT '类型：1-增加，2-扣减',
                                             `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
                                             `experience` int(11) NOT NULL DEFAULT '0' COMMENT '经验',
                                             `balance` int(11) NOT NULL DEFAULT '0' COMMENT '剩余',
                                             `mark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                                             `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：1-成功（保留字段）',
                                             `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                                             `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                             PRIMARY KEY (`id`) USING BTREE,
                                             KEY `openid` (`uid`) USING BTREE,
                                             KEY `status` (`status`) USING BTREE,
                                             KEY `add_time` (`create_time`) USING BTREE,
                                             KEY `type` (`type`) USING BTREE,
                                             KEY `type_link` (`type`,`link_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户经验记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user_experience_record`
--


/*!40000 ALTER TABLE `eb_user_experience_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_user_experience_record` ENABLE KEYS */;


--
-- Table structure for table `eb_user_extract`
--

DROP TABLE IF EXISTS `eb_user_extract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user_extract` (
                                   `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                                   `uid` int(10) unsigned DEFAULT NULL,
                                   `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
                                   `extract_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'bank' COMMENT 'bank = 银行卡 alipay = 支付宝 weixin=微信',
                                   `bank_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '银行卡',
                                   `bank_address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '开户地址',
                                   `alipay_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '支付宝账号',
                                   `extract_price` decimal(8,2) unsigned DEFAULT '0.00' COMMENT '提现金额',
                                   `mark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                                   `balance` decimal(8,2) unsigned DEFAULT '0.00',
                                   `fail_msg` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '无效原因',
                                   `status` tinyint(2) DEFAULT '0' COMMENT '-1 未通过 0 审核中 1 已提现',
                                   `wechat` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信号',
                                   `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `fail_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '失败时间',
                                   `bank_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '银行名称',
                                   `qrcode_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信收款二维码',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   KEY `extract_type` (`extract_type`) USING BTREE,
                                   KEY `status` (`status`) USING BTREE,
                                   KEY `openid` (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户提现表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user_extract`
--


/*!40000 ALTER TABLE `eb_user_extract` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_user_extract` ENABLE KEYS */;


--
-- Table structure for table `eb_user_group`
--

DROP TABLE IF EXISTS `eb_user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user_group` (
                                 `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                                 `group_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户分组名称',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户分组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user_group`
--


/*!40000 ALTER TABLE `eb_user_group` DISABLE KEYS */;
INSERT INTO `eb_user_group` VALUES (1,'初级会员'),(2,'中级会员'),(3,'高级会员');
/*!40000 ALTER TABLE `eb_user_group` ENABLE KEYS */;


--
-- Table structure for table `eb_user_integral_record`
--

DROP TABLE IF EXISTS `eb_user_integral_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user_integral_record` (
                                           `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '记录id',
                                           `uid` int(10) NOT NULL DEFAULT '0' COMMENT '用户uid',
                                           `link_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '关联id-orderNo,(sign,system默认为0）',
                                           `link_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'order' COMMENT '关联类型（order,sign,system）',
                                           `type` int(1) NOT NULL DEFAULT '1' COMMENT '类型：1-增加，2-扣减',
                                           `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
                                           `integral` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
                                           `balance` int(11) NOT NULL DEFAULT '0' COMMENT '剩余',
                                           `mark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                                           `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款）',
                                           `frozen_time` int(3) NOT NULL DEFAULT '0' COMMENT '冻结期时间（天）',
                                           `thaw_time` bigint(14) NOT NULL DEFAULT '0' COMMENT '解冻时间',
                                           `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                                           `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                           PRIMARY KEY (`id`) USING BTREE,
                                           KEY `openid` (`uid`) USING BTREE,
                                           KEY `status` (`status`) USING BTREE,
                                           KEY `add_time` (`create_time`) USING BTREE,
                                           KEY `type` (`type`) USING BTREE,
                                           KEY `type_link` (`type`,`link_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户积分记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user_integral_record`
--


/*!40000 ALTER TABLE `eb_user_integral_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_user_integral_record` ENABLE KEYS */;


--
-- Table structure for table `eb_user_level`
--

DROP TABLE IF EXISTS `eb_user_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user_level` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `uid` int(11) NOT NULL DEFAULT '0' COMMENT '用户uid',
                                 `level_id` int(11) NOT NULL DEFAULT '0' COMMENT '等级vip',
                                 `grade` int(11) NOT NULL DEFAULT '0' COMMENT '会员等级',
                                 `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:禁止,1:正常',
                                 `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                                 `remind` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已通知',
                                 `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0=未删除,1=删除',
                                 `discount` int(4) NOT NULL DEFAULT '100' COMMENT '享受折扣',
                                 `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `expired_time` timestamp NULL DEFAULT NULL COMMENT '过期时间',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户等级记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user_level`
--


/*!40000 ALTER TABLE `eb_user_level` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_user_level` ENABLE KEYS */;


--
-- Table structure for table `eb_user_recharge`
--

DROP TABLE IF EXISTS `eb_user_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user_recharge` (
                                    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                                    `uid` int(10) DEFAULT NULL COMMENT '充值用户UID',
                                    `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '订单号',
                                    `price` decimal(8,2) DEFAULT NULL COMMENT '充值金额',
                                    `give_price` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '购买赠送金额',
                                    `recharge_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '充值类型',
                                    `paid` tinyint(1) DEFAULT '0' COMMENT '是否充值',
                                    `pay_time` timestamp NULL DEFAULT NULL COMMENT '充值支付时间',
                                    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '充值时间',
                                    `refund_price` decimal(10,2) DEFAULT '0.00' COMMENT '退款金额',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE KEY `order_id` (`order_id`) USING BTREE,
                                    KEY `uid` (`uid`) USING BTREE,
                                    KEY `recharge_type` (`recharge_type`) USING BTREE,
                                    KEY `paid` (`paid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户充值表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user_recharge`
--


/*!40000 ALTER TABLE `eb_user_recharge` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_user_recharge` ENABLE KEYS */;


--
-- Table structure for table `eb_user_sign`
--

DROP TABLE IF EXISTS `eb_user_sign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user_sign` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `uid` int(11) NOT NULL DEFAULT '0' COMMENT '用户uid',
                                `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '签到说明',
                                `number` int(11) NOT NULL DEFAULT '0' COMMENT '获得',
                                `balance` int(11) NOT NULL DEFAULT '0' COMMENT '剩余',
                                `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '类型，1积分，2经验',
                                `create_day` date NOT NULL COMMENT '签到日期',
                                `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                KEY `uid` (`uid`,`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='签到记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user_sign`
--


/*!40000 ALTER TABLE `eb_user_sign` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_user_sign` ENABLE KEYS */;


--
-- Table structure for table `eb_user_tag`
--

DROP TABLE IF EXISTS `eb_user_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user_tag` (
                               `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                               `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标签名称',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='标签管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user_tag`
--


/*!40000 ALTER TABLE `eb_user_tag` DISABLE KEYS */;
INSERT INTO `eb_user_tag` VALUES (1,'中级c'),(2,'高级'),(3,'黄金'),(4,'超级'),(5,'钻石');
/*!40000 ALTER TABLE `eb_user_tag` ENABLE KEYS */;


--
-- Table structure for table `eb_user_token`
--

DROP TABLE IF EXISTS `eb_user_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user_token` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `uid` int(10) unsigned NOT NULL COMMENT '用户 id',
                                 `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'token',
                                 `type` tinyint(1) DEFAULT '1' COMMENT '类型，1 公众号， 2 小程序, 3 unionid, 5AppIos,6AppAndroid,7ios',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `expires_time` datetime DEFAULT NULL COMMENT '到期时间',
                                 `login_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录ip',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE KEY `type+token` (`type`,`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user_token`
--


/*!40000 ALTER TABLE `eb_user_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_user_token` ENABLE KEYS */;


--
-- Table structure for table `eb_user_visit_record`
--

DROP TABLE IF EXISTS `eb_user_visit_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eb_user_visit_record` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `date` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日期',
                                        `uid` int(11) DEFAULT NULL COMMENT '用户uid',
                                        `visit_type` int(2) DEFAULT NULL COMMENT '访问类型',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        KEY `date` (`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户访问记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eb_user_visit_record`
--


/*!40000 ALTER TABLE `eb_user_visit_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `eb_user_visit_record` ENABLE KEYS */;
