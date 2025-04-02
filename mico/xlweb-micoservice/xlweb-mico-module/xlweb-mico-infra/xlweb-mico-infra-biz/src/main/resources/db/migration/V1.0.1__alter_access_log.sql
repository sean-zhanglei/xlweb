ALTER TABLE `xlweb_mico_infra`.`eb_infra_api_access_log`
    CHANGE COLUMN `result_msg` `result_msg` VARCHAR(1024) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NULL DEFAULT '' COMMENT '结果提示' ;
