CREATE TABLE `history` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `student_id` int DEFAULT NULL COMMENT '学生ID',
  `dish_id` int DEFAULT NULL COMMENT '菜品ID',
  `time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '浏览时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='浏览记录表';