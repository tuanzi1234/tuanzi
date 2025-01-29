CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `student_id` int DEFAULT NULL COMMENT '学生ID',
  `order_no` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单编号',
  `create_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '下单时间',
  `pay_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付时间',
  `pay_no` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付单号',
  `pay_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付方式',
  `price` decimal(10,2) DEFAULT NULL COMMENT '订单价格',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单信息表';