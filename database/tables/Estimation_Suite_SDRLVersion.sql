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
-- Table structure for table `SDRLVersion`
--

DROP TABLE IF EXISTS `SDRLVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SDRLVersion` (
  `idSDRLVersion` int(11) NOT NULL AUTO_INCREMENT,
  `idSDRL` int(11) NOT NULL,
  `SDRL_Title` varchar(45) NOT NULL,
  `Version_Number` varchar(45) NOT NULL,
  `SDRL_Description` varchar(1000) DEFAULT NULL,
  `Based_On` int(11) DEFAULT NULL,
  PRIMARY KEY (`idSDRLVersion`),
  UNIQUE KEY `unique_SDRLVERSION` (`idSDRL`,`Version_Number`),
  KEY `fk_SDRLVersion_idSDRL_idx` (`idSDRL`),
  CONSTRAINT `fk_SDRLVersion_idSDRL` FOREIGN KEY (`idSDRL`) REFERENCES `SDRL` (`idSDRL`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SDRLVersion`
--

LOCK TABLES `SDRLVersion` WRITE;
/*!40000 ALTER TABLE `SDRLVersion` DISABLE KEYS */;
INSERT INTO `SDRLVersion` VALUES (6,162,'Tstt','1','This is a test',NULL),(7,163,'Tstt','1','This is a test',6),(8,164,'SDRL 1','1','This is SDRL 1',NULL),(9,165,'SDRL 2','1','This is SDRL 2',NULL),(10,166,'SDRL 3','1','This is SDRL 3',NULL),(11,167,'SDRL 1','1','This is SDRL 1',8),(12,168,'SDRL 2','1','This is SDRL 2',9),(13,169,'SDRL 3','1','This is SDRL 3',10),(14,170,'SDRL 1','1','This is SDRL 1',11),(15,171,'SDRL 2','1','This is SDRL 2',12),(16,172,'SDRL 3','1','This is SDRL 3',13),(17,173,'SDRL 1','1','This is SDRL 1',14),(18,174,'SDRL 2','1','This is SDRL 2',15),(19,175,'SDRL 3','1','This is SDRL 3',16),(20,176,'SDRL 1','1','This is SDRL 1',17),(21,177,'SDRL 2','1','This is SDRL 2',18),(22,178,'SDRL 3','1','This is SDRL 3',19),(23,179,'SDRL 1','1','THIS IS SDRL 1',NULL),(24,180,'Tstt','1','This is a test',7),(25,181,'1','1','21',NULL);
/*!40000 ALTER TABLE `SDRLVersion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-10 17:10:45
