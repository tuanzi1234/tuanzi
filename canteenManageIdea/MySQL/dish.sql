CREATE TABLE `dish` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜品名称',
  `img` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜品主图',
  `category_id` int DEFAULT NULL COMMENT '分类ID',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '菜品描述',
  `price` double(10,2) DEFAULT '0.00' COMMENT '菜品价格',
  `num` int DEFAULT '0' COMMENT '剩余份数',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上架状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜品信息表';