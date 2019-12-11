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
-- Table structure for table `TaskVersion`
--

DROP TABLE IF EXISTS `TaskVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TaskVersion` (
  `idTaskVersion` int(11) NOT NULL AUTO_INCREMENT,
  `idTask` int(11) NOT NULL,
  `Task_Name` varchar(45) NOT NULL,
  `Estimate_Formula` varchar(45) NOT NULL,
  `Version_Number` varchar(45) NOT NULL,
  `Staff_Hours` varchar(45) NOT NULL,
  `Task_Details` varchar(1000) NOT NULL,
  `Assumptions` varchar(1000) NOT NULL,
  `Estimate_Methodology` varchar(1000) NOT NULL,
  `PoP_Start` date DEFAULT NULL,
  `PoP_End` date DEFAULT NULL,
  `Based_On` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTaskVersion`),
  UNIQUE KEY `unique_TaskVersion` (`idTask`,`Version_Number`),
  KEY `fk_TaskVersion_idTask_idx` (`idTask`),
  CONSTRAINT `fk_TaskVersion_idTask` FOREIGN KEY (`idTask`) REFERENCES `Task` (`idTask`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TaskVersion`
--

LOCK TABLES `TaskVersion` WRITE;
/*!40000 ALTER TABLE `TaskVersion` DISABLE KEYS */;
INSERT INTO `TaskVersion` VALUES (21,72,'arst','arst','1','1','arst','ararsarst','tsrarsat','2019-12-01','2019-12-23',NULL),(22,73,'a','a','1','1','a','a','a','2019-12-01','2019-12-16',NULL),(28,74,'ss','ss','1','1','ss','ss','ss','2019-12-03','2019-12-24',NULL);
/*!40000 ALTER TABLE `TaskVersion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-10 17:10:15
