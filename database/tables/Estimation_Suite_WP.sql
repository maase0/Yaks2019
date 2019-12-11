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
-- Table structure for table `WP`
--

DROP TABLE IF EXISTS `WP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WP` (
  `idWP` int(11) NOT NULL AUTO_INCREMENT,
  `idOrganizationVersion` int(11) NOT NULL,
  `WP_Name` varchar(45) NOT NULL,
  `Based_On` int(11) DEFAULT NULL,
  PRIMARY KEY (`idWP`),
  KEY `FK_WP_idOrganizationVersion_idx` (`idOrganizationVersion`),
  CONSTRAINT `FK_WP_idOrganizationVersion` FOREIGN KEY (`idOrganizationVersion`) REFERENCES `OrganizationVersion` (`idOrganizationVersion`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WP`
--

LOCK TABLES `WP` WRITE;
/*!40000 ALTER TABLE `WP` DISABLE KEYS */;
INSERT INTO `WP` VALUES (22,11,'A',NULL),(23,12,'Work Package',NULL),(24,12,'Work Package',NULL),(25,12,'Test',NULL),(26,12,'A',NULL),(27,12,'A',NULL),(28,12,'A',NULL),(29,12,'A',NULL),(30,14,'a',NULL),(31,15,'a',NULL),(32,15,'a',31),(33,16,'A',NULL),(34,16,'A',33),(35,17,'a',NULL),(36,18,'a',35),(37,19,'a',36),(38,19,'b',NULL),(39,20,'',37),(45,20,'arst',NULL),(46,21,'st',NULL);
/*!40000 ALTER TABLE `WP` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-10 17:10:36
