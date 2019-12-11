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
-- Table structure for table `CLINVersion`
--

DROP TABLE IF EXISTS `CLINVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CLINVersion` (
  `idCLINVersion` int(11) NOT NULL AUTO_INCREMENT,
  `idCLIN` int(11) NOT NULL,
  `CLIN_Index` varchar(45) NOT NULL,
  `Version_Number` varchar(45) NOT NULL,
  `Project_Type` varchar(45) NOT NULL,
  `CLIN_Description` varchar(1000) NOT NULL,
  `PoP_Start` varchar(45) DEFAULT NULL,
  `PoP_End` date DEFAULT NULL,
  `Based_On` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCLINVersion`),
  UNIQUE KEY `unique_CLINVersion` (`idCLIN`,`Version_Number`),
  KEY `fk_CLINVersion_idCLIN_idx` (`idCLIN`),
  CONSTRAINT `fk_CLINVersion_idCLIN` FOREIGN KEY (`idCLIN`) REFERENCES `CLIN` (`idCLIN`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLINVersion`
--

LOCK TABLES `CLINVersion` WRITE;
/*!40000 ALTER TABLE `CLINVersion` DISABLE KEYS */;
INSERT INTO `CLINVersion` VALUES (8,166,'10','1','Test','This is a test','2019-12-06','2019-12-06',NULL),(9,166,'10','2','Test','This is a test\nwith a version change','2019-12-06','2019-12-04',8),(10,168,'10','2','Test','This is a test\nwith a version change','2019-12-06','2019-12-27',9),(13,170,'12','1','Test','This is a test of the new saving\nWith an edit?','2019-12-05','2019-12-12',NULL),(14,170,'12','1.1','Test','This is a test of the new saving\nWith an edit?\nand a version change?','2019-12-05','2019-12-05',13),(15,171,'10','1','Test','This is a test','2019-12-04','2019-12-18',NULL),(18,174,'10','1','Test','This is a test','2019-12-04','2019-12-16',15),(19,175,'1','1','1 CLIN','This is a CLIN 1','2019-12-01','2019-12-11',NULL),(20,176,'2','1','2 CLIN','This is CLIN 2','2019-12-01','2019-12-16',NULL),(21,177,'3','1','3 CLIN','This is CLIN 3','2019-12-02','2019-12-11',NULL),(22,178,'1','1','1 CLIN','This is a CLIN 1','2019-12-01','2019-12-10',19),(23,179,'2','1','2 CLIN','This is CLIN 2','2019-12-01','2019-12-15',20),(24,180,'3','1','3 CLIN','This is CLIN 3','2019-12-02','2019-12-10',21),(25,181,'1','1','1 CLIN','This is a CLIN 1','2019-12-01','2019-12-09',22),(26,182,'2','1','2 CLIN','This is CLIN 2','2019-12-01','2019-12-14',23),(27,183,'3','1','3 CLIN','This is CLIN 3','2019-12-02','2019-12-09',24),(28,184,'1','1','1 CLIN','This is a CLIN 1','2019-12-01','2019-12-08',25),(29,185,'2','1','2 CLIN','This is CLIN 2','2019-12-01','2019-12-13',26),(30,186,'3','1','3 CLIN','This is CLIN 3','2019-12-02','2019-12-08',27),(31,187,'1','1','1 CLIN','This is a CLIN 1','2019-12-01','2019-12-05',28),(32,188,'2','1','2 CLIN','This is CLIN 2','2019-12-01','2019-12-10',29),(33,189,'3','1','3 CLIN','This is CLIN 3','2019-12-02','2019-12-05',30),(34,190,'1','1','CLIN 1','This is CLIN 1','2019-12-01','2019-12-01',NULL),(35,191,'10','2','Test','This is a test\nwith a version change','2019-12-06','2019-12-23',10),(36,192,'12','1.1','Test','This is a test of the new saving\nWith an edit?\nand a version change?','2019-12-01','2019-12-06',14),(37,193,'1','1','21','12','2019-12-01','2019-12-02',NULL),(38,194,'10','1','Test','This is a test','2019-12-04','2019-12-14',18),(39,195,'11','1','arst','arstarst','2019-12-06','2020-01-03',NULL),(40,195,'11','1.1','arst','arstarst\nnew version','2019-12-06','2020-01-02',39),(41,196,'1','1','Test','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus laoreet, nisi vitae pretium commodo, justo dui faucibus urna, \neu sodales nibh magna vitae quam. Vestibulum ac nibh posuere, finibus erat sed, pellentesque enim. Interdum et malesuada fames \nac ante ipsum primis in faucibus. Fusce sed bibendum neque. Aenean vel justo quis sem mattis ultrices sit amet a ipsum. Aliquam a\n massa a turpis porttitor ornare quis eu sem. Quisque ullamcorper lacus tortor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ','2019-12-11','2019-12-13',NULL),(42,197,'2','1','Test','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec posuere, tellus eu sagittis vehicula, \nmi lorem faucibus leo, et faucibus felis sapien id dui. Duis ut lorem malesuada, tincidunt libero et, \naliquet mauris. Ut vel nisl fringilla, feugiat metus tristique, porta nunc. Curabitur laoreet eros non sem ornare elementum. ','2019-12-11','2019-12-12',NULL);
/*!40000 ALTER TABLE `CLINVersion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-10 17:10:12
