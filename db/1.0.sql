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

DROP TABLE IF EXISTS `jjjshop_app_mp`;
CREATE TABLE `jjjshop_app_mp`  (
                                   `app_id` int(10) UNSIGNED NOT NULL COMMENT 'appid',
                                   `mpapp_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '公众号AppID',
                                   `mpapp_secret` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '公众号AppSecret',
                                   `is_delete` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除',
                                   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                   PRIMARY KEY (`app_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '微信公众号记录表' ROW_FORMAT = DYNAMIC;

INSERT INTO `jjjshop_app_mp` (`app_id`, `mpapp_id`, `mpapp_secret`, `is_delete`, `create_time`, `update_time`) VALUES (10001, '10001', '1', 0, '2022-10-14 11:44:35', NULL);

