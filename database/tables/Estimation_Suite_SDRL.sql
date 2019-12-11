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
-- Table structure for table `SDRL`
--

DROP TABLE IF EXISTS `SDRL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SDRL` (
  `idSDRL` int(11) NOT NULL AUTO_INCREMENT,
  `idProjectVersion` int(11) NOT NULL,
  `SDRL_Title` varchar(45) NOT NULL,
  `Based_On` int(11) DEFAULT NULL,
  PRIMARY KEY (`idSDRL`),
  KEY `FK_idProjectVersion_idx` (`idProjectVersion`),
  CONSTRAINT `FK_SDRL_idProjectVersion` FOREIGN KEY (`idProjectVersion`) REFERENCES `ProjectVersion` (`idProjectVersion`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SDRL`
--

LOCK TABLES `SDRL` WRITE;
/*!40000 ALTER TABLE `SDRL` DISABLE KEYS */;
INSERT INTO `SDRL` VALUES (162,233,'Tstt',NULL),(163,235,'Tstt',162),(164,240,'SDRL 1',NULL),(165,240,'SDRL 2',NULL),(166,240,'SDRL 3',NULL),(167,241,'SDRL 1',164),(168,241,'SDRL 2',165),(169,241,'SDRL 3',166),(170,242,'SDRL 1',167),(171,242,'SDRL 2',168),(172,242,'SDRL 3',169),(173,243,'SDRL 1',170),(174,243,'SDRL 2',171),(175,243,'SDRL 3',172),(176,244,'SDRL 1',173),(177,244,'SDRL 2',174),(178,244,'SDRL 3',175),(179,245,'SDRL 1',NULL),(180,246,'Tstt',163),(181,247,'1',NULL);
/*!40000 ALTER TABLE `SDRL` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-10 17:10:18
