-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: j10d206.p.ssafy.io    Database: ssafy
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `book_group` int DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `info` mediumtext,
  `isbn` varchar(255) NOT NULL,
  `page` int DEFAULT NULL,
  `pub_date` varchar(255) DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `rating` int DEFAULT '0',
  `reviewer_cnt` int DEFAULT '0',
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  KEY `idx_books_title` (`title`),
  KEY `idx_books_author` (`author`)
) ENGINE=InnoDB AUTO_INCREMENT=114987 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `book_genre`
--

DROP TABLE IF EXISTS `book_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_genre` (
  `book_genre_id` int NOT NULL AUTO_INCREMENT,
  `book_id` int DEFAULT NULL,
  `genre_id` int DEFAULT NULL,
  PRIMARY KEY (`book_genre_id`),
  KEY `FK52evq6pdc5ypanf41bij5u218` (`book_id`),
  KEY `FK8l6ops8exmjrlr89hmfow4mmo` (`genre_id`),
  CONSTRAINT `FK52evq6pdc5ypanf41bij5u218` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
  CONSTRAINT `FK8l6ops8exmjrlr89hmfow4mmo` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=130666 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bookshelf`
--

DROP TABLE IF EXISTS `bookshelf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookshelf` (
  `bookshelf_id` int NOT NULL AUTO_INCREMENT,
  `cover` varchar(255) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `is_rate` int DEFAULT NULL,
  `is_read` int DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `book_id` int DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`bookshelf_id`),
  KEY `FKg9fjuh3yp4mdxxp6dga60gorv` (`book_id`),
  KEY `FKo5unf2diy86jonydekqc2au1l` (`member_id`),
  CONSTRAINT `FKg9fjuh3yp4mdxxp6dga60gorv` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
  CONSTRAINT `FKo5unf2diy86jonydekqc2au1l` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `card` (
  `card_id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `create_at` date DEFAULT NULL,
  `deleted_by_recipient` tinyint(1) DEFAULT '0',
  `deleted_by_sender` tinyint(1) DEFAULT '0',
  `book_id` int DEFAULT NULL,
  `from_member_id` bigint DEFAULT NULL,
  `to_member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`card_id`),
  KEY `FKqvyyyvll9fchctrr7ph43dbn0` (`book_id`),
  KEY `FKb8sbep6gdyq3af6iy541mv53h` (`from_member_id`),
  KEY `FKkoai3wflj26lnsnikv9x8r0gp` (`to_member_id`),
  CONSTRAINT `FKb8sbep6gdyq3af6iy541mv53h` FOREIGN KEY (`from_member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKkoai3wflj26lnsnikv9x8r0gp` FOREIGN KEY (`to_member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKqvyyyvll9fchctrr7ph43dbn0` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `book_id` int DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FKkko96rdq8d82wm91vh2jsfak7` (`book_id`),
  KEY `FKmrrrpi513ssu63i2783jyiv9m` (`member_id`),
  CONSTRAINT `FKkko96rdq8d82wm91vh2jsfak7` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
  CONSTRAINT `FKmrrrpi513ssu63i2783jyiv9m` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=464 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_verified`
--

DROP TABLE IF EXISTS `email_verified`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_verified` (
  `code` int NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follow` (
  `follow_id` int NOT NULL AUTO_INCREMENT,
  `follower` bigint DEFAULT NULL,
  `following` bigint DEFAULT NULL,
  PRIMARY KEY (`follow_id`),
  KEY `FKon174ytyau6iulr7uyxfoui4m` (`follower`),
  KEY `FK3afh80u5bklfqjtbsmwgrn76` (`following`),
  CONSTRAINT `FK3afh80u5bklfqjtbsmwgrn76` FOREIGN KEY (`following`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKon174ytyau6iulr7uyxfoui4m` FOREIGN KEY (`follower`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre` (
  `genre_id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_recommend_book`
--

DROP TABLE IF EXISTS `group_recommend_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_recommend_book` (
  `group_recommend_book_id` int NOT NULL AUTO_INCREMENT,
  `cover` varchar(255) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `re_group` int DEFAULT NULL,
  `book_id` int DEFAULT NULL,
  PRIMARY KEY (`group_recommend_book_id`),
  KEY `FK6qyx3d9to7frtjm8c1cosxd6e` (`book_id`),
  CONSTRAINT `FK6qyx3d9to7frtjm8c1cosxd6e` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `guest_book`
--

DROP TABLE IF EXISTS `guest_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guest_book` (
  `guest_book_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(120) NOT NULL,
  `created_at` date DEFAULT NULL,
  `from_member_id` bigint DEFAULT NULL,
  `to_member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`guest_book_id`),
  KEY `FKp9ha5dr2k7o7mdirsmkelcljp` (`from_member_id`),
  KEY `FKs91e2x67k4ekhnr8cbjpikb2a` (`to_member_id`),
  CONSTRAINT `FKp9ha5dr2k7o7mdirsmkelcljp` FOREIGN KEY (`from_member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKs91e2x67k4ekhnr8cbjpikb2a` FOREIGN KEY (`to_member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_id` bigint NOT NULL AUTO_INCREMENT,
  `birth` int NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` int NOT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `is_disabled` int DEFAULT NULL,
  `is_receivable` int NOT NULL,
  `member_group` int DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `naver_code` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `shelf_group` int DEFAULT NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `UK_hh9kg6jti4n1eoiertn2k6qsc` (`nickname`),
  UNIQUE KEY `UK_mbmcqelty0fbrvxp1q58dn57t` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `member_recommend_book`
--

DROP TABLE IF EXISTS `member_recommend_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_recommend_book` (
  `member_recommend_id` int NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `book_id` int DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`member_recommend_id`),
  KEY `FK2mx5icxua8ppmhpa2ehg2y776` (`book_id`),
  KEY `FKdpq5j3aic1lft1g3b5fv3egvf` (`member_id`),
  CONSTRAINT `FK2mx5icxua8ppmhpa2ehg2y776` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
  CONSTRAINT `FKdpq5j3aic1lft1g3b5fv3egvf` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2621 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `postbox`
--

DROP TABLE IF EXISTS `postbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `postbox` (
  `postbox_id` int NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `card_id` bigint DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`postbox_id`),
  KEY `FKc0oi6ffa4fjt2v8tade57duw2` (`card_id`),
  KEY `FK778x558b6kjumhl9euaufvgmy` (`member_id`),
  CONSTRAINT `FK778x558b6kjumhl9euaufvgmy` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKc0oi6ffa4fjt2v8tade57duw2` FOREIGN KEY (`card_id`) REFERENCES `card` (`card_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `refresh_entity`
--

DROP TABLE IF EXISTS `refresh_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_entity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `expiration` varchar(255) DEFAULT NULL,
  `refresh` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=353 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-02  9:12:45
