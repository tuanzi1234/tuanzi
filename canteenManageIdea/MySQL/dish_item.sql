CREATE TABLE `dish_item` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `student_id` int DEFAULT NULL COMMENT '学生ID',
  `dish_id` int DEFAULT NULL COMMENT '菜品ID',
  `num` int DEFAULT '1' COMMENT '分数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='点单信息表';