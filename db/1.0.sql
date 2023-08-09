ALTER TABLE `jjjshop_order_refund_image`
    MODIFY COLUMN `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' AFTER `app_id`;

ALTER TABLE jjjshop_store_order MODIFY COLUMN `create_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
