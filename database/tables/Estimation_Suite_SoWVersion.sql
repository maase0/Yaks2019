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
-- Table structure for table `SoWVersion`
--

DROP TABLE IF EXISTS `SoWVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SoWVersion` (
  `idSoWVersion` int(11) NOT NULL AUTO_INCREMENT,
  `idSoW` int(11) NOT NULL,
  `Reference_Number` int(11) NOT NULL,
  `Version_Number` varchar(45) NOT NULL,
  `SoW_Description` varchar(1000) DEFAULT NULL,
  `Based_On` int(11) DEFAULT NULL,
  PRIMARY KEY (`idSoWVersion`),
  UNIQUE KEY `unique_SoWVersion` (`idSoW`,`Version_Number`),
  KEY `fk_SoWVersion_idSoW_idx` (`idSoW`),
  CONSTRAINT `fk_SoWVersion_idSoW` FOREIGN KEY (`idSoW`) REFERENCES `SoW` (`idSoW`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SoWVersion`
--

LOCK TABLES `SoWVersion` WRITE;
/*!40000 ALTER TABLE `SoWVersion` DISABLE KEYS */;
INSERT INTO `SoWVersion` VALUES (4,107,1,'1','arst\nedit',NULL),(5,107,1,'2','arst\nedit\nversion change',4),(7,108,1,'1','A',NULL),(8,109,1,'1','A',NULL),(9,110,1,'1','A',NULL),(10,111,1,'1','arst',NULL),(11,111,1,'2','arst',10),(14,112,1,'2','arst',11),(15,113,1,'1','This is SOW 1',NULL),(16,114,2,'1','This is SOW 2',NULL),(17,115,3,'1','This is SOW 3',NULL),(18,116,1,'1','This is SOW 1',15),(19,117,2,'1','This is SOW 2',16),(20,118,3,'1','This is SOW 3',17),(21,119,1,'1','This is SOW 1',18),(22,120,2,'1','This is SOW 2',19),(23,121,3,'1','This is SOW 3',20),(24,122,1,'1','This is SOW 1',21),(25,123,2,'1','This is SOW 2',22),(26,124,3,'1','This is SOW 3',23),(27,125,1,'1','This is SOW 1',24),(28,126,2,'1','This is SOW 2',25),(29,127,3,'1','This is SOW 3',26),(30,128,1,'1','This is SOW 1',NULL),(31,129,1,'1','A',7),(32,130,1,'1','A',8),(33,131,1,'1','A',9),(34,132,21,'1','21',NULL),(35,133,1,'2','arst',14);
/*!40000 ALTER TABLE `SoWVersion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-10 17:10:06
