CREATE TABLE `feedback` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `student_id` int DEFAULT NULL COMMENT '学生ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `question` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '问题',
  `idea` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '想法',
  `create_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '反馈时间',
  `content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '回复内容',
  `reply_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='反馈建议表';