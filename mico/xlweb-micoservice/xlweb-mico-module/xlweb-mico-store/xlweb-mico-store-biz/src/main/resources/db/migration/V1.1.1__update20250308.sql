ALTER TABLE `xlweb_mico_store`.`eb_store_order`
    ADD COLUMN `delivery_time` VARCHAR(100) NULL DEFAULT '' AFTER `out_trade_no`,
ADD COLUMN `pickup_time` VARCHAR(100) NULL DEFAULT '' AFTER `delivery_time`;