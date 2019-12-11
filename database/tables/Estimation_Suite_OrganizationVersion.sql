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
-- Table structure for table `OrganizationVersion`
--

DROP TABLE IF EXISTS `OrganizationVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrganizationVersion` (
  `idOrganizationVersion` int(11) NOT NULL AUTO_INCREMENT,
  `idOrganization` int(11) NOT NULL,
  `Organization_Name` varchar(45) NOT NULL,
  `Version_Number` varchar(45) NOT NULL,
  `Product` varchar(100) NOT NULL,
  `Based_On` int(11) DEFAULT NULL,
  PRIMARY KEY (`idOrganizationVersion`),
  UNIQUE KEY `unique_OrganizationVersion` (`idOrganization`,`Version_Number`),
  KEY `fk_OrganizationVersion_idOrganization_idx` (`idOrganization`),
  CONSTRAINT `fk_OrganizationVersion_idOrganization` FOREIGN KEY (`idOrganization`) REFERENCES `Organization` (`idOrganization`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrganizationVersion`
--

LOCK TABLES `OrganizationVersion` WRITE;
/*!40000 ALTER TABLE `OrganizationVersion` DISABLE KEYS */;
INSERT INTO `OrganizationVersion` VALUES (10,24,'ASDF','1','GOOD',NULL),(11,24,'ASDF','1.1','GOOD',10),(12,25,'A','1','V',NULL),(14,25,'A','1.2','V',12),(15,25,'A','1.3','V',14),(16,25,'A','1.4','V',15),(17,25,'A','1.5','V',16),(18,25,'A','1.6','V',17),(19,25,'A','1.7','V',18),(20,25,'A','1.8','V',19),(21,26,'arst','1','arst',NULL),(26,31,'Testinf','1','Testing',NULL);
/*!40000 ALTER TABLE `OrganizationVersion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-10 17:10:39
