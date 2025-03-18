-- ----------------------------
-- Table structure for eb_system_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `eb_system_operate_log`;
CREATE TABLE `eb_system_operate_log`  (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
                                       `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
                                       `user_id` bigint NOT NULL COMMENT '用户编号',
                                       `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
                                       `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作模块类型',
                                       `sub_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作名',
                                       `biz_id` bigint NOT NULL COMMENT '操作数据模块编号',
                                       `action` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作内容',
                                       `success` bit(1) NOT NULL DEFAULT b'1' COMMENT '操作结果',
                                       `extra` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '拓展字段',
                                       `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求方法名',
                                       `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求地址',
                                       `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户 IP',
                                       `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '浏览器 UA',
                                       `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                       `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                       `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9064 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志记录 V2 版本';

-- ----------------------------
-- Records of eb_system_operate_log
-- ----------------------------

-- ----------------------------
-- Table structure for eb_system_login_log
-- ----------------------------
DROP TABLE IF EXISTS `eb_system_login_log`;
CREATE TABLE `eb_system_login_log`  (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
                                     `log_type` bigint NOT NULL COMMENT '日志类型',
                                     `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
                                     `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户编号',
                                     `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
                                     `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户账号',
                                     `result` tinyint NOT NULL COMMENT '登陆结果',
                                     `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
                                     `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
                                     `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                     `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3415 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统访问记录';

-- ----------------------------
-- Records of eb_system_login_log
-- ----------------------------
