CREATE DATABASE  IF NOT EXISTS `Estimation_Suite` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `Estimation_Suite`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: Estimation_Suite
-- ------------------------------------------------------
-- Server version	5.7.28-0ubuntu0.18.04.4

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
-- Table structure for table `WPVersion`
--

DROP TABLE IF EXISTS `WPVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WPVersion` (
  `idWPVersion` int(11) NOT NULL AUTO_INCREMENT,
  `idWP` int(11) NOT NULL,
  `Version_Number` double NOT NULL,
  `WP_Name` varchar(45) NOT NULL,
  `BoE_Author` varchar(45) NOT NULL,
  `Scope` varchar(45) NOT NULL,
  `WP_Type` varchar(45) NOT NULL,
  `Type_of_Work` varchar(45) NOT NULL,
  `PoP_Start` date DEFAULT NULL,
  `PoP_End` date DEFAULT NULL,
  `Based_On` int(11) DEFAULT NULL,
  PRIMARY KEY (`idWPVersion`),
  UNIQUE KEY `unique_WPVersion` (`idWP`,`Version_Number`),
  KEY `fk_WPVersion_idWP_idx` (`idWP`),
  CONSTRAINT `fk_WPVersion_idWP` FOREIGN KEY (`idWP`) REFERENCES `WP` (`idWP`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WPVersion`
--

LOCK TABLES `WPVersion` WRITE;
/*!40000 ALTER TABLE `WPVersion` DISABLE KEYS */;
INSERT INTO `WPVersion` VALUES (11,26,1,'A','V','V','null','V','2019-12-05','2019-12-11',NULL),(12,26,1.1,'A','V','V','null','V','2019-12-04','2019-12-10',11),(13,27,1.1,'A','V','V','null','V','2019-12-03','2019-12-09',NULL),(14,28,1.1,'A','V','V','null','V','2019-12-03','2019-12-09',NULL),(15,29,1.1,'A','V','V','null','V','2019-12-02','2019-12-08',NULL),(16,30,1,'a','a','a','null','a','2019-12-11','2019-12-18',NULL),(17,31,1,'a','aa','a','null','a','2019-12-03','2019-12-11',NULL),(18,32,1,'a','aa','a','null','a','2019-12-02','2019-12-10',17),(19,33,1,'A','A','A','null','A','2019-12-03','2019-12-12',NULL),(20,34,1,'A','A','A','null','A','2019-12-02','2019-12-11',19),(21,35,1,'a','a','a','null','a','2019-12-04','2019-12-11',NULL),(22,36,1,'a','a','a','null','a','2019-12-03','2019-12-10',21),(23,37,1,'a','a','a','null','a','2019-12-01','2019-12-08',22),(24,38,1,'b','b','b','null','b','2019-11-24','2019-11-26',NULL),(25,37,1.1,'a','a','a','null','a','2019-11-29','2019-12-06',23),(26,37,1.2,'a','a','a','null','a','2019-11-27','2019-12-04',25),(27,37,1.3,'a','a','a','null','a','2019-11-25','2019-12-02',26),(28,37,1.4,'a','a','a','null','a','2019-11-23','2019-11-30',27),(29,37,1.5,'','a','a','null','a','2019-11-22','2019-11-29',28),(30,39,1.5,'arst','a','a','null','a','2019-10-31','2019-11-07',29),(36,45,1,'arst','arst','arst','null','arstst','2019-12-08','2019-12-15',NULL),(37,46,1,'st','st','st','null','st','2019-12-02','2019-12-18',NULL);
/*!40000 ALTER TABLE `WPVersion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-10 17:10:09
