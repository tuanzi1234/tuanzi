CREATE TABLE `orders_item` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `dish_id` int DEFAULT NULL COMMENT '菜品ID',
  `num` int DEFAULT NULL COMMENT '份数',
  `score` int DEFAULT '0' COMMENT '评分',
  `comment` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评价',
  `student_id` int DEFAULT NULL COMMENT '学生ID',
  `order_id` int DEFAULT NULL COMMENT '订单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单明细表';
