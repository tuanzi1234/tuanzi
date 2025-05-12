-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: canteen_manage
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '公告内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (21,'炸鸡排下架','经学生反映，炸鸡排用料不新鲜，现已下架炸鸡排，今后将彻查原材料','2025-04-25 02:54:52'),(22,'胡辣汤已经售完，现已下架','经反映，胡辣汤由于太受欢迎，供不应求，今后会增加胡辣汤的量','2025-04-25 02:58:25'),(23,'关于第二食堂下周一下午部分窗口暂停服务的通知','为提升食堂服务质量，第二食堂定于2025年4月28日（下周一）下午 14:00 至 16:00 进行员工服务技能培训。培训期间，第二食堂二楼风味窗口将暂停服务，一楼基本伙食窗口正常供应。请需要在此时间段用餐的师生选择其他食堂或提前就餐。不便之处，敬请理解。','2025-04-25 03:08:26'),(24,' 关于举行消防应急疏散演练（涉及第一食堂区域）的通知','为提高师生消防安全意识和应急处置能力，学校定于2025年5月8日（下周四）下午15:00举行消防应急疏散演练。演练范围包括教学楼A座及第一食堂。届时，第一食堂区域将有模拟烟雾并拉响警报，请在食堂内就餐的师生保持镇定，听从现场工作人员指挥，有序疏散至指定安全区域。演练预计持续30分钟。请大家积极配合。','2025-04-25 03:09:37'),(25,'食堂招聘勤工助学学生助理若干名',' 因工作需要，现面向全校学生招聘食堂勤工助学岗位助理若干名，主要负责协助窗口服务、卫生清洁、餐具整理等工作。要求：在校学生，身体健康，吃苦耐劳，责任心强，能保证每周工作时间不少于10小时。待遇按学校勤工助学标准发放。有意者请于5月10日前携带学生证到各食堂办公室报名或咨询详情。','2025-04-25 03:10:19'),(26,'关于智慧食堂系统将于本周日凌晨进行维护升级的通知','尊敬的用户：为提升系统性能及用户体验，智慧食堂系统计划于 2025年4月27日（本周日）凌晨 2:00 至 4:00 进行系统维护升级。维护期间，线上点餐、支付及余额查询等功能将暂停服务。线下窗口支付不受影响。给您带来的不便，敬请谅解。感谢您的支持与配合！','2025-04-25 03:11:03');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-26 15:13:06
