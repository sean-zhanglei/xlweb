ALTER TABLE `xlweb_mico_store`.`eb_store_order_status`
    ADD COLUMN `id` INT(11) NOT NULL AUTO_INCREMENT AFTER `create_time`,
ADD PRIMARY KEY (`id`);
;
