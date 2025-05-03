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
-- Table structure for table `information`
--

DROP TABLE IF EXISTS `information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `information` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '封面',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布时间',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布人',
  `num` int DEFAULT '0' COMMENT '浏览量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='食堂资讯表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `information`
--

LOCK TABLES `information` WRITE;
/*!40000 ALTER TABLE `information` DISABLE KEYS */;
INSERT INTO `information` VALUES (2,'http://localhost:9090/files/download/1745432714900-期末.png','【期末助力 💪】部分菜品限时 8 折优惠！','<p><br></p><p style=\"text-align: start;\"><br></p><p><strong>【期末助力 💪】部分菜品限时 8 折优惠！</strong></p><ul><li>发布日期：2025年1月26日</li><li>发布单位：各食堂运营部</li></ul><hr/><p><br></p><h4><strong>备考冲刺，食堂为你加油！</strong></h4><p>同学们，紧张的期末考试周即将到来，为了助力大家全力备考，<strong>所有食堂</strong>将在<strong>6月23日至6月27日</strong>推出“期末助力能量套餐”！</p><p><strong>优惠详情：</strong></p><ol><li><strong>指定菜品享受 8 折优惠！</strong>（套餐包含主食+米饭）</li><li>套餐菜品每日更新，确保营养均衡。</li><li>凭本人校园卡在<strong>套餐窗口</strong>购买即可享受优惠。</li></ol><blockquote>“吃得好，才能学得好！” —— 食堂温馨提示</blockquote><p>祝大家复习顺利，考试超常发挥！</p>','2025-01-26 02:35:45','admin001',31),(3,'http://localhost:9090/files/download/1745433049520-劳动节通知.png','【重要通知】五一劳动节期间食堂开放时间调整','<p><br></p><p><strong>【重要通知】五一劳动节期间食堂开放时间调整</strong></p><ul><li>发布日期：2025年1月26日</li><li>发布单位：后勤保障部 食堂管理中心</li></ul><hr/><p><br></p><h3><strong>关于2025年五一劳动节期间食堂供餐安排的通知</strong></h3><p>全体师生：</p><p>根据国家法定节假日安排及学校通知，现将2025年五一劳动节期间（5月1日至5月5日）食堂供餐时间及开放窗口调整如下：</p><ul><li><span style=\"color: rgb(225, 60, 57);\"><strong>第一食堂：</strong></span></li></ul><p><span style=\"color: rgb(225, 60, 57);\">一楼基本伙食窗口：正常开放 (早餐 7:00-9:00, 午餐 11:00-13:00, 晚餐 17:00-19:00)</span></p><p><span style=\"color: rgb(225, 60, 57);\">二楼风味窗口：部分开放，具体请见食堂门口通知。</span></p><ul><li><span style=\"color: rgb(66, 144, 247);\"><strong>第二食堂：</strong></span></li></ul><p><span style=\"color: rgb(66, 144, 247);\">一楼、二楼：正常开放，供餐时间同上。</span></p><ul><li><span style=\"color: rgb(245, 219, 77);\"><strong>所有食堂夜宵服务：</strong></span><span style=\"color: rgb(245, 219, 77);\"> 暂停供应 (5月1日晚至5月5日晚)。</span></li></ul><p><strong>请注意：</strong></p><ul><li>节日期间部分窗口可能轮休，请大家留意食堂内具体通知。</li><li>节日后 (5月6日) 所有食堂恢复正常供餐。</li></ul><p>请大家相互转告，合理安排就餐。预祝大家度过一个愉快的假期！</p>','2025-01-26 02:35:45','admin001',8),(4,'http://localhost:9090/files/download/1745433244833-食品安全.png','【食安关注】开展食品安全卫生大检查通知','<p style=\"text-align: start;\"><br></p><p><strong>【食安关注】开展食品安全卫生大检查通知</strong></p><ul><li>发布日期：2025年1月26日</li><li>发布单位：食堂质量监督小组</li></ul><hr/><p><br></p><h3><strong>保障舌尖安全，共建放心食堂</strong></h3><p>尊敬的师生员工：</p><p>为进一步加强食堂食品安全管理，确保广大师生饮食安全与健康，学校后勤保障部及食堂管理中心决定于 <strong>2025年5月7日至5月9日</strong> 对各食堂进行一次全面的食品安全卫生大检查。</p><p><strong>检查重点包括：</strong></p><ul><li>原材料采购、存储与溯源管理</li><li>食品加工操作规范执行情况</li><li>餐饮用具清洗消毒状况</li><li>从业人员健康及卫生要求</li><li>后厨环境卫生</li></ul><p>检查期间可能会对部分区域的供餐产生短暂影响，敬请谅解。我们欢迎广大师生对食堂卫生进行监督，如发现任何问题，可通过以下途径反馈：</p><ol><li>食堂意见箱</li><li>智慧食堂系统内的“意见反馈”功能</li><li>监督电话：<code>[虚拟电话号码 010-12345678]</code></li></ol><p><strong>食品安全无小事，感谢您的理解与支持！</strong></p>','2025-01-26 03:21:52','admin001',8),(5,'http://localhost:9090/files/download/1745433515817-早餐推荐.png','【健康“食”光】☀️ 开启活力一天，营养早餐推荐！','<p style=\"text-align: start;\"><br></p><p><strong>【健康“食”光】☀️ 开启活力一天，营养早餐推荐！</strong></p><ul><li>发布日期：2025年1月26日</li><li>发布单位：食堂营养健康顾问</li></ul><hr/><p><br></p><h3><strong>一日之计在于晨，健康早餐吃什么？</strong></h3><p>同学们，早餐是一天中最重要的一餐，它为你的学习和活动提供基础能量。还在为早餐吃什么发愁吗？食堂为你推荐几款营养搭配：</p><p><strong>推荐搭配：</strong></p><ol><li><strong>全麦三明治 + 牛奶/豆浆 + 水煮蛋:</strong> 提供优质蛋白质、碳水化合物和膳食纤维。</li><li><strong>杂粮粥 + 包子/馒头 + 凉拌小菜:</strong> 中式经典搭配，温和养胃，营养均衡。</li><li><strong>酸奶 + 水果 + 燕麦片:</strong> 富含益生菌、维生素和膳食纤维，清爽健康。</li></ol><p><strong>健康小贴士：</strong></p><ul><li>尽量保证早餐有主食、蛋白质和蔬果。</li><li>避免油炸、高糖分的早餐选项。</li><li>规律吃早餐有助于维持血糖稳定，提高上午的学习效率。</li></ul><p><em>今天，你吃早餐了吗？</em> 前往各食堂早餐窗口，选择你的活力搭配吧！</p>','2025-01-26 03:21:52','admin001',11),(6,'http://localhost:9090/files/download/1745433642696-问卷调查.png','【“食”话实说】🗣️ 食堂服务满意度问卷调查','<p><strong>【“食”话实说】🗣️ 食堂服务满意度问卷调查</strong></p><ul><li>发布日期：2025年1月26日</li><li>发布单位：食堂管理中心</li></ul><hr/><p style=\"text-align: start;\"><br></p><h4><strong>您的意见，我们“食”分重视！</strong></h4><p>亲爱的同学们、老师们：</p><p>为了不断提升食堂的服务质量和菜品口味，更好地满足大家的就餐需求，我们诚挚邀请您参与本次食堂服务满意度问卷调查。</p><p>您的宝贵意见和建议，将是我们改进工作、优化服务的重要依据。</p><p><strong>问卷内容：</strong></p><ul><li>菜品口味与种类</li><li>价格合理性</li><li>就餐环境卫生</li><li>服务人员态度</li><li>供餐效率</li></ul><p><strong>参与方式：</strong></p><ul><li>点击链接直接参与：<code>[此处放置一个虚拟链接 http://survey.example.com/canteen]</code></li></ul><p><strong>截止日期：</strong> 2025年5月25日</p><p>我们将从有效问卷中随机抽取 <strong>50名</strong> 幸运参与者，赠送 <strong>20元</strong> 食堂代金券！</p><p>感谢您的参与和支持！</p>','2025-01-26 03:21:52','admin001',10),(7,'http://localhost:9090/files/download/1745434248549-讲文明.png','【文明就餐】🍽️ 共同维护整洁环境，餐盘请放回收处','<p style=\"text-align: start;\"><br></p><p><strong>【文明就餐】🍽️ 共同维护整洁环境，餐盘请放回收处</strong></p><ul><li>发布日期：2025年1月26日</li><li>发布单位：后勤保障部 & 青年志愿者协会</li></ul><hr/><p><br></p><h3><strong>举手之劳，方便大家</strong></h3><p>同学们：</p><p>干净整洁的就餐环境需要我们共同维护。近期发现部分同学用餐后未将餐盘、餐具放置到指定的回收处，影响了后续同学的就餐体验，也增加了保洁人员的工作负担。</p><p><strong>我们在此温馨提示：</strong></p><ol><li>用餐完毕后，请自觉将<strong>餐盘、碗筷、纸巾</strong>等放入<strong>餐具回收传送带</strong>或<strong>指定回收桶</strong>。</li><li>请进行简单的<strong>食物残渣分类</strong>（如有分类垃圾桶）。</li><li>保持桌面整洁，方便下一位同学使用。</li></ol><blockquote>小细节体现大文明，感谢您的配合！</blockquote><p>让我们一起努力，营造更舒适、更有序的就餐环境。</p>','2025-01-26 03:32:42','admin001',3),(8,'http://localhost:9090/files/download/1745434561014-价格调整.png','【价格公示】关于部分菜品价格调整的说明','<p><br></p><p><strong>【价格公示】关于部分菜品价格调整的说明</strong></p><ul><li>发布日期：2025年1月26日</li><li>发布单位：食堂成本核算办公室</li></ul><hr/><p><br></p><h3><strong>关于部分食堂菜品价格调整的公示说明</strong></h3><p>尊敬的各位师生：</p><p>近期，受市场原材料（特别是猪肉、食用油等）价格持续上涨影响，为保障菜品质量和食堂的可持续运营，经学校批准，我们将对部分菜品价格进行小幅调整。</p><p><strong>主要调整涉及：</strong></p><ul><li>部分荤菜（如红烧肉、糖醋里脊等）</li><li>部分油炸类食品</li></ul><p><strong>具体调整明细：</strong></p><p><br></p><table style=\"width: 100%;\"><tbody><tr><th colSpan=\"1\" rowSpan=\"1\" width=\"auto\">菜品名称</th><th colSpan=\"1\" rowSpan=\"1\" width=\"auto\">原价格 (元)</th><th colSpan=\"1\" rowSpan=\"1\" width=\"auto\">新价格 (元)</th></tr><tr><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">红烧肉</td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">12</td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">13</td></tr><tr><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">糖醋里脊</td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">14</td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">15</td></tr><tr><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">炸鸡排</td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">10</td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">11</td></tr></tbody></table><p><em>（注：此为示例，具体调整菜品及价格请以各食堂窗口公示为准）</em></p><p><strong>价格调整生效日期：</strong> <strong>2025年5月1日</strong></p><p><strong>我们承诺：</strong></p><ol><li><strong>基本伙食</strong>（如米饭、基础素菜、汤等）价格<strong>保持稳定</strong>。</li><li>继续严控成本，优化采购渠道，尽可能降低价格上涨对师生的影响。</li><li>坚持提升菜品质量和服务水平，确保物有所值。</li></ol><p>感谢大家的理解与支持！</p><p><br></p>','2025-01-26 03:35:59','admin001',2),(9,'http://localhost:9090/files/download/1745434766554-川味新品.png','【新品驾到】🌶️ 麻辣鲜香！川味水煮肉片惊艳亮相！','<p><strong>【新品驾到】🌶️ 麻辣鲜香！川味水煮肉片惊艳亮相！</strong></p><ul><li>发布日期：2025年1月26日</li><li>发布单位：第一食堂 风味窗口</li></ul><hr/><p><br></p><h2><strong>无辣不欢？这款新品不容错过！</strong></h2><p>各位“食”客们请注意！</p><p><strong>第一食堂二楼【川湘风味】窗口</strong> 本周重磅推出经典川菜——<strong>水煮肉片</strong>！</p><ul><li>精选<strong>嫩滑里脊肉</strong>，配以<strong>豆芽、青笋</strong>等爽口时蔬。</li><li>淋上秘制<strong>麻辣红油</strong>，撒上<strong>干辣椒、花椒</strong>，热油一泼，香气四溢！</li><li><strong>麻、辣、鲜、香、烫</strong>，五味俱全，口感丰富，绝对下饭！</li></ul><p><strong>价格：</strong> 15元/份</p><p><strong>供应时间：</strong> 午餐、晚餐时段</p><p><strong>温馨提示：</strong> 辣度可选（微辣/中辣），不能吃辣的同学请谨慎尝试哦！</p><p>快来一食堂二楼，让这道热辣滚烫的水煮肉片点燃你的味蕾吧！</p><p><img src=\"http://localhost:9090/files/download/1745607524139-水煮肉片.jpg\" alt=\"\" data-href=\"\" style=\"width: 396.32px;height: 297.24px;\"/></p>','2025-01-26 03:37:04','admin001',6);
/*!40000 ALTER TABLE `information` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-26 15:12:48
