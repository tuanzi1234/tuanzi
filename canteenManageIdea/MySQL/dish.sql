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
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜品名称',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜品主图',
  `category_id` int DEFAULT NULL COMMENT '分类ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '菜品描述',
  `price` double(10,2) DEFAULT '0.00' COMMENT '菜品价格',
  `num` int DEFAULT '0' COMMENT '剩余份数',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上架状态',
  `sales` int DEFAULT '0' COMMENT '销量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜品信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES (1,'红烧肉','http://localhost:9090/files/download/1742882888137-红烧肉.jpg',34,'红烧肉（英文名：Red braised pork belly），一道著名的大众菜肴，各大菜系都有自己特色的红烧肉。肥而不腻，香甜松软，营养丰富，入口即化。',13.00,21,'上架',5),(2,'回锅肉','http://localhost:9090/files/download/1737311269312-回锅肉.jpg',34,'回锅肉口味独特，色泽红亮，肥而不腻，色香味俱全，是下饭菜中大部分人会选的菜。川菜中的回锅肉，最佳是蒜苗、仔姜、青椒做“翘头”，佐以郫县豆瓣、永川豆豉。而自贡家常菜中的回锅肉，就有青椒、仔姜、葱白配，藠头配，干豇豆配，莲花白配，甚至锅盔配。',15.00,28,'上架',5),(3,'老母鸡汤','http://localhost:9090/files/download/1737311840232-老母鸡汤.jpg',34,'老母鸡汤就是用老母鸡煮的汤，老母鸡宰杀后除去内脏等外部杂物洗净，与葱、姜、料酒、精盐同时放入开水锅内，用慢火焖煮（水不沸腾为宜，使鸡肉中蛋白质、脂肪等营养物质溢出）。约煮3小时，直至鸡肉脱骨，加入味精，即可食用。',20.00,20,'上架',1),(7,'水饺','http://localhost:9090/files/download/1737372731141-水饺.jpg',36,'饺子的特点是皮薄馅嫩，味道鲜美，形状独特，百食不厌。饺子的制作原料营养素种类齐全，蒸煮法保证营养较少流失，并且符合中国色香味饮食文化的内涵。饺子是一种历史悠久的民间吃食，深受老百姓的欢迎，民间有“好吃不过饺子”的俗语。每逢新春佳节，饺子更成为一种应时不可缺少的佳肴。\n饺子在河南省、河北省还有山西省、山东省一些地方方言中也被称为“扁食”。',12.00,42,'上架',2),(8,'黄焖鸡','http://localhost:9090/files/download/1737372918197-黄焖鸡.jpg',34,'黄焖鸡又叫香鸡煲、浓汁鸡煲，属于鲁菜系家常菜品，起源于山东省济南市。主要食材是鸡腿肉，配以青椒、土豆、香菇等焖制而成，味道美妙，具有肉质鲜美嫩滑的特点。\n',13.00,48,'上架',1),(12,'猪脚饭','http://localhost:9090/files/download/1737472862758-猪脚饭.jpg',36,'猪脚饭其特色在于使用秘制的中药材卤料配方，通常包含香叶、八角、桂皮、陈皮、小茴香等成分。“隆江猪脚”发展至今已有一千多年历史，其味道鲜美，口感弹嫩糯香，肥而不腻，入口香爽，让人回味悠长，征服了无数食客挑剔的味蕾。',15.00,54,'上架',1),(13,'红烧牛肉面','http://localhost:9090/files/download/1737472970109-红烧牛肉面.jpg',36,'红烧牛肉面是以牛腱肉、切面等为主要材料制作的食物，口味鲜香，为中华传统面食。形成了以兰州牛肉拉面、徐派菜牛肉面、四川牛肉面等诸多以地域命名的牛肉面饮食。',14.00,28,'上架',2),(14,'扬州炒饭','http://localhost:9090/files/download/1737473107504-扬州炒饭.jpg',36,'扬州炒饭又名扬州蛋炒饭，是江苏省扬州市的一道传统名菜，属于淮扬菜，其主要食材有米饭、火腿、鸡蛋、虾仁等。\n扬州炒饭选料严谨、制作精细、加工讲究，而且注重配色。炒制完成后，颗粒分明、粒粒松散、软硬有度、色彩调和、光泽饱满、配料多样、鲜嫩滑爽、香糯可口。',13.00,56,'上架',1),(15,'螺蛳粉','http://localhost:9090/files/download/1737655907935-螺蛳粉.jpg',36,'螺蛳粉（Liuzhou Luosifen） [20]，是广西壮族自治区柳州市的特色小吃之一，具有辣、爽、鲜、酸、烫的独特风味。是柳州最具地方特色的名小吃。螺蛳粉的味美还因为它有着独特的汤料。汤料由螺蛳、山奈、八角、肉桂、丁香、多种辣椒、等天然香料和味素配制而成。2018年8月20日，“柳州螺蛳粉”获得国家地理标志商标。2008年，柳州螺蛳粉手工制作技艺入选广西壮族自治区第二批非物质文化遗产名录。2020年被列入国家级非物质文化遗产名单。',12.00,60,'上架',0),(19,'酸菜鱼','http://localhost:9090/files/download/1738413682143-酸菜鱼.jpg',35,'酸菜鱼也称为酸汤鱼，是一道源自重庆市的经典菜品，以其特有的调味和独特的烹调技法而著称。流行于20世纪90年代，是重庆江湖菜的开路先锋之一。\n酸菜鱼以草鱼为主料，配以泡菜等食材煮制而成，口味酸辣可口；鱼含丰富优质蛋白，能提供人丰富的蛋白质、矿物质等营养；酸菜中的乳酸可以促进人体对铁元素的吸收，还可以增加人的食欲。\n关于酸菜鱼的历史来源众说纷纭，也无法考证，后经传承，制作方法也各有不同，但口味基本一致。',14.00,50,'上架',0),(27,'燕麦粥','http://localhost:9090/files/download/1745513193949-燕麦粥.jpg',37,'精选澳洲燕麦与鲜奶熬制，低脂高纤，智能恒温保鲜，营养随时享用。',3.00,144,'上架',0),(28,'牛奶','http://localhost:9090/files/download/1745514001875-牛奶.jpg',37,'高校专属奶源，巴氏杀菌冷链直达，高钙高蛋白，实时温度监控保鲜。',3.00,123,'上架',0),(29,'杂粮粥','http://localhost:9090/files/download/1745514217022-杂粮粥.png',37,'杂粮粥是以稻米为主要食材，配以杂粮熬制的粥。根据原材料和功能，有红枣首乌黑发粥、木瓜美容养生粥、五豆粥等。\n杂粮粥制作简单，营养丰富，有益健康。',3.00,45,'上架',0),(30,'三明治','http://localhost:9090/files/download/1745516652688-三明治.jpg',37,'三明治（英语：Sandwich）是指一切在两片以上的面包或薄饼中间放置馅料如肉、奶酪或蔬菜等食物，加上调味料、酱汁任意搭配在一起的料理。其中的面包经常会涂上少许沙拉酱、奶油、花生酱、调味油或其他调味料烘烤以改善味道和口感。三明治食用及携带方便，故经常作为正餐或郊游、远足、旅行时的餐点。',6.00,54,'上架',0),(31,'豆浆','http://localhost:9090/files/download/1745516938304-豆浆.jpg',37,'豆浆又称豆乳、豆奶、豆腐浆，是指以黄豆或黑豆研磨而成的浆汁，富含植物性蛋白质与微量钙质，是素食者的优良蛋白质摄取来源。',1.50,54,'上架',0),(32,'茶叶蛋','http://localhost:9090/files/download/1745518289091-茶叶蛋.jpg',37,'茶叶蛋，又名茶蛋、盐茶蛋、五香蛋、五香茶叶蛋、茶鸡蛋，是一种历史悠久的蛋类料理',2.00,64,'上架',0),(33,'肉包子','http://localhost:9090/files/download/1745518607881-包子.jpg',37,'包子是中国的传统食品，一般是用面粉混和水后发酵过的面团内馅蒸制而成，款式非常多。常用馅为各种肉类或各类蔬菜、芝麻、豆沙等等。现今出名的包子有天津狗不理包子、广东叉烧包，小笼包，灌汤包，上海生煎包。',1.50,53,'上架',0),(34,'馒头','http://localhost:9090/files/download/1745518798967-馒头.jpg',37,'馒头，古称“蛮头”，别称“馍”“馍馍”“蒸馍”，为“包子”的本称，中国传统面食之一，是一种用发酵的面蒸成的食品。馒头以小麦面粉为主要原料，是中国人日常主食之一',3.00,56,'上架',0),(35,'胡辣汤','http://localhost:9090/files/download/1745519039029-胡辣汤.jpg',37,'胡辣汤是一种流行于中国河南省的小吃，通常由牛肉（或羊肉）、面筋、腐皮、胡椒、辣椒等食材制成，汤汁浓稠，口味咸鲜麻辣，常作为早餐',3.00,0,'上架',0),(36,'糖醋里脊','http://localhost:9090/files/download/1745519420468-糖醋里脊.jpg',34,'糖醋里脊是中国经典传统名菜之一，该菜品以猪里脊肉为主材，配以面粉、淀粉、醋、番茄酱等佐料，酸甜可口，让人食欲大开；该菜品在湘菜、陕菜、豫菜、浙菜、鲁菜、川菜、淮扬菜、粤菜、闽菜里均有此菜',15.00,65,'上架',0),(37,'炸鸡排','http://localhost:9090/files/download/1745519548322-炸鸡排.jpg',34,'鸡排（香港称为鸡扒，台湾称为鸡腿排），是块状的鸡肉，通常是鸡的胸部或腿部，为西餐中最常见的食物之一，一般来说都带骨，口感会比较好。鸡腿排的烹调方法以煎、炸和烧烤为主。蒸则最为健康',11.00,42,'下架',0),(38,'水煮肉片','http://localhost:9090/files/download/1745519660938-水煮肉片.jpg',34,'水煮肉片（Poached spicy slices of pork）是以猪里脊肉为主料的一道地方新创名菜，起源于四川地区-四川省-自贡市，发扬于西南，属于川菜中著名的家常菜',15.00,53,'上架',0);
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-26 15:11:40
