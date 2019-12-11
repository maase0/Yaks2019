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
-- Table structure for table `ProjectVersion`
--

DROP TABLE IF EXISTS `ProjectVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ProjectVersion` (
  `idProjectVersion` int(11) NOT NULL AUTO_INCREMENT,
  `idProject` int(11) NOT NULL,
  `Version_Number` varchar(45) NOT NULL,
  `Project_Name` varchar(45) NOT NULL,
  `Project_Manager` varchar(45) NOT NULL,
  `Proposal_Number` varchar(45) NOT NULL,
  `PoP_Start` date DEFAULT NULL,
  `PoP_End` date DEFAULT NULL,
  `Based_On` double DEFAULT NULL,
  PRIMARY KEY (`idProjectVersion`),
  UNIQUE KEY `unique_version` (`idProject`,`Version_Number`),
  KEY `fkidProject_idx` (`idProject`),
  CONSTRAINT `FK_ProjectVersion_idProject` FOREIGN KEY (`idProject`) REFERENCES `Project` (`idProject`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=257 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectVersion`
--

LOCK TABLES `ProjectVersion` WRITE;
/*!40000 ALTER TABLE `ProjectVersion` DISABLE KEYS */;
INSERT INTO `ProjectVersion` VALUES (233,212,'1','a','Erich Maar','1','2019-11-21','2019-12-21',NULL),(235,212,'1.1','Submitted Example 2','Erich Maar','1','2019-10-27','2019-12-28',233),(236,213,'1','Save Test','Erich Maas','1','2019-12-03','2019-12-03',NULL),(239,213,'2','Save Test','Erich Maas','1','2019-12-01','2019-12-01',236),(240,214,'1','Unsubmitted Example','Project Manager','1','2019-12-02','2020-01-01',NULL),(241,214,'2','Unsubmitted Example','Project Manager','1','2019-12-01','2019-12-31',240),(242,214,'2.1','Unsubmitted Example','Project Manager','1','2019-11-30','2019-12-30',241),(243,214,'2.2','Unsubmitted Example','Project Manager','1','2019-11-29','2019-12-29',242),(244,214,'3','Unsubmitted Example','Project Manager','1','2019-11-26','2019-12-26',243),(245,215,'3','Submitted Example','Project Manager','1','2019-11-30','2023-12-20',NULL),(246,212,'1.2','a','Erich Maar','1','2018-10-03','2020-12-18',235),(247,216,'1','Submitted Example 2','sbsdf','1','2019-11-30','2020-01-02',NULL),(248,213,'2.2','Save Test','Erich Maas','1','2019-11-29','2019-11-29',239),(249,217,'1','Project 1','Erich Maas','1','2019-12-06','2019-12-13',NULL),(250,218,'1','Project 2','Erich Maas','1','2019-12-06','2019-12-13',NULL),(251,219,'1','Project 3','Reno Levari','1','2019-12-06','2019-12-07',NULL),(252,220,'1','Project 4','Josiah Bell','445','2019-12-06','2019-12-20',NULL),(253,218,'1.1','Project 2','Erich Maas','1','2019-12-05','2019-12-12',250),(254,218,'1.2','Project 2','Erich Maas','1','2019-12-04','2019-12-11',253),(255,218,'1.2.1','Project 2','Erich Maas','1','2019-12-03','2019-12-10',254),(256,221,'1','Projcet 5','Erich Maas','123','2019-12-05','2019-12-28',NULL);
/*!40000 ALTER TABLE `ProjectVersion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-10 17:10:21
