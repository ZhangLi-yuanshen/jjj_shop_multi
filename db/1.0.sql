ALTER TABLE `jjjshop_order_refund_image`
    MODIFY COLUMN `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' AFTER `app_id`;

ALTER TABLE jjjshop_store_order
    MODIFY COLUMN `create_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';

ALTER TABLE jjjshop_printer
    MODIFY COLUMN `create_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    MODIFY COLUMN `update_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间';

ALTER TABLE jjjshop_user_visit
    MODIFY COLUMN `create_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    MODIFY COLUMN `update_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间';

ALTER TABLE jjjshop_order_refund_image
    MODIFY COLUMN `create_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
