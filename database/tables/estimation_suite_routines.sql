CREATE DATABASE  IF NOT EXISTS `estimation_suite` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `estimation_suite`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: estimation_suite
-- ------------------------------------------------------
-- Server version	5.5.62

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping events for database 'estimation_suite'
--

--
-- Dumping routines for database 'estimation_suite'
--
/*!50003 DROP PROCEDURE IF EXISTS `approve_project` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `approve_project`(PID INT, currentDate DATE)
BEGIN
    #Check if project is already denied, UI should not allow this but regardless.
    #Then set Approved_Date to today's date.
	UPDATE Project SET
    Approved_Date = currentDate
    WHERE (idProject = PID AND Denied_Date IS NULL);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `check_status` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `check_status`(PID int)
BEGIN
	#Checks approved/denied status for an estimated project. Returns result in @state variable.
	SELECT Estimated_Date FROM Project 
    WHERE (idProject = PID) INTO @estimated;
    
    SELECT Approved_Date FROM Project
    WHERE (idProject = PID) INTO @approved;
    
    SELECT Denied_Date FROM Project
    WHERE (idProject = PID) INTO @denied;
    
	IF (@approved IS NOT NULL) THEN
		SET @state = "Approved";
    ELSEIF (@denied IS NOT NULL) THEN
		SET @state = "Denied";
	ELSEIF (@estimated IS NOT NULL AND @approved IS NULL AND @denied IS NULL) THEN
		SET @state = "Pending";
	ELSE
		SET @state = NULL;
    END IF;
    
    SELECT @state AS `status`;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `clone_clin` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `clone_clin`(VID int, CLINVID int, clinIndex VARCHAR(45), versionNumber VARCHAR(45),  projectType VARCHAR(45), clinDescription VARCHAR(1000), popStart DATE, popEnd DATE)
BEGIN
	#used with update_projectVersion when the version number changes to clone old version's CLINs
	SELECT idCLIN FROM CLINVersion 
    WHERE idCLINVersion = CLINVID INTO @CLINID;
    
    INSERT INTO CLIN (idProjectVersion, CLIN_Index, Based_On)
    VALUE (VID, clinIndex, @CLINID);
    
    SET @CLINID = LAST_INSERT_ID();
    
	INSERT INTO CLINVersion (idCLIN, CLIN_Index, Version_Number, Project_Type, CLIN_Description, PoP_Start, PoP_End, Based_On) 
    VALUE (@CLINID, clinIndex, versionNumber, projectType, clinDescription, popStart, popEnd, CLINVID);
    
    SET @CLINVID = LAST_INSERT_ID();
    SELECT * FROM CLINVersion WHERE (idCLINVersion = @CLINVID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `clone_organization` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `clone_organization`(CLINVID int, ORGVID int, orgName VARCHAR(45), versionNumber VARCHAR(45), product VARCHAR(100))
BEGIN
	#used with update_projectVersion or clin when the version number changes to clone old version's Organizations
    SELECT idOrganization FROM OrganizationVersion 
    WHERE (idOrganizationVersion = ORGVID) INTO @ORGID;
    
    INSERT INTO `Organization` (idCLINVersion, Organization_Name, Based_On)
    VALUES (CLINVID, orgName, @ORGID);
    
    SET @ORGID = LAST_INSERT_ID();
    
	INSERT INTO `OrganizationVersion` (idOrganization, Organization_Name, Version_Number, Product, Based_On)
    VALUES (@ORGID, orgName, versionNumber, product, ORGVID);
    
    SET @ORGVID = LAST_INSERT_ID();
    SELECT * FROM `OrganizationVersion` WHERE idOrganizationVersion = @ORGVID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `clone_sdrl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `clone_sdrl`(VID int, SDRLVID int, sdrlTitle VARCHAR(45), versionNumber VARCHAR(45), sdrlDescription VARCHAR(1000))
BEGIN
	#used with update_projectVersion when the version number changes to clone old version's SDRLs
	SELECT idSDRL FROM SDRLVersion 
    WHERE idSDRLVersion = SDRLVID INTO @SDRLID;
    
    INSERT INTO SDRL (idProjectVersion, SDRL_Title, Based_On)
    VALUE (VID, sdrlTitle, @SDRLID);
    
    SET @SDRLID = LAST_INSERT_ID();
    
	INSERT INTO SDRLVersion (idSDRL, SDRL_Title, Version_Number, SDRL_Description, Based_On) 
    VALUE (@SDRLID, sdrlTitle, versionNumber, sdrlDescription, SDRLVID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `clone_sow` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `clone_sow`(VID int, SOWVID int, sowRef VARCHAR(45), versionNumber VARCHAR(45), sowDescription VARCHAR(1000))
BEGIN
	#used with update_projectVersion when the version number changes to clone old version's SOWs
    SELECT idSoW FROM SoWVersion 
    WHERE idSoWVersion = SOWVID INTO @SOWID;
    
    INSERT INTO SoW (idProjectVersion, Reference_Number, Based_On)
    VALUE (VID, sowRef, @SOWID);
    
    SET @SOWID = LAST_INSERT_ID();
    
	INSERT INTO SoWVersion (idSoW, Reference_Number, Version_Number, SoW_Description, Based_On)
    VALUE (@SOWID, sowRef, versionNumber, sowDescription, SOWVID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `clone_task` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `clone_task`(WPVID int, TASKVID int, taskName varchar(45), versionNumber VARCHAR(45), estimateFormula varchar(45), staffHours varchar(45), taskDetails varchar(1000), assumptions varchar(1000), methodology varchar(1000), popStart DATE, popEnd DATE)
BEGIN
	#used with update_projectVersion or WP when the version number changes to clone old version's Tasks
    SELECT idTask FROM TaskVersion 
    WHERE (idTaskVersion = TASKVID) INTO @TASKID;
    
    INSERT INTO Task (idWPVersion, Task_Name, Based_On)
    VALUES (WPVID, taskName, @TASKID);
    
    SET @TASKID = LAST_INSERT_ID();
    
    INSERT INTO TaskVersion (idTask, Task_Name, Version_Number, Estimate_Formula, Staff_Hours, Task_Details, Assumptions, Estimate_Methodology, PoP_Start, PoP_End, Based_On)
    VALUES (@TASKID, taskName, versionNumber, estimateFormula, staffHours, taskDetails, assumptions, methodology, popStart, popEnd, TASKVID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `clone_wp` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `clone_wp`(ORGVID int, WPVID int, versionNumber varchar(45), wpName varchar(45), boeAuthor varchar(45), scope varchar(45), wpType varchar(45), typeOfWork varchar(45), popStart DATE, popEnd DATE)
BEGIN
	#used with update_projectVersion or Organization when the version number changes to clone old version's WP
    SELECT idWP FROM WPVersion 
    WHERE (idWPVersion = WPVID) INTO @WPID;
    
    INSERT INTO WP (idOrganizationVersion, WP_Name, Based_On)
    VALUES (ORGVID, wpName, @WPID);
    
    SET @WPID = LAST_INSERT_ID();
    
	INSERT INTO WPVersion (idWP, Version_Number, WP_Name, BoE_Author, Scope, WP_Type, Type_of_Work, PoP_Start, PoP_End, Based_On)
    VALUES (@WPID, versionNumber, wpName, boeAuthor, scope, wpType, typeOfWork, popStart, popEnd, WPVID);

	SET @WPVID = LAST_INSERT_ID();

    SELECT * FROM WPVersion WHERE idWPVersion = @WPVID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_clin` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_clin`(CLINID int)
BEGIN
	#Deletes specified CLIN which then cascades to associated orgs to work packages to tasks.
	DELETE FROM CLIN WHERE (idCLIN = CLINID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_organization` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_organization`(ORGID int)
BEGIN
	#Deletes specified Organization which then cascades to associated tasks
	DELETE FROM `Organization` WHERE (idOrganization = ORGID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_project` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_project`(PID int)
BEGIN
	#Deletes specified project then cascades to everything else.
	DELETE FROM Project WHERE (idProject = PID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_projectVersion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_projectVersion`(VID INT)
BEGIN
	#Deletes specified version of project and then cascades to everything it has.
	DELETE FROM ProjectVersion WHERE (idProjectVersion = VID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_sdrl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_sdrl`(SDRLID int)
BEGIN
	#Deletes specified SDRL.
	DELETE FROM SDRL WHERE (idSDRL = SDRLID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_sow` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_sow`(SoWID int)
BEGIN
	#Deletes specified SoW.
	DELETE FROM SoW WHERE (idSoW = SoWID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_task` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_task`(TASKID int)
BEGIN
	#Deletes specified task.
	DELETE FROM Task WHERE (idTask = TASKID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_wp` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_wp`(WPID int)
BEGIN
	#Deletes specified work package and then cascades to associated tasks.
	DELETE FROM WP WHERE (idWP = WPID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `deny_project` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `deny_project`(PID INT, currentDate DATE)
BEGIN
    #Check if project is already approved, UI should not allow this but regardless.
    #Then set Denied_Date to today's date.
	UPDATE Project SET
    Denied_Date = currentDate
    WHERE (idProject = PID AND Approved_Date IS NULL);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `estimate_project` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `estimate_project`(PID INT, currentDate DATE)
BEGIN
	#Sets project's Estimated_Date to current date.
	UPDATE Project SET
    Estimated_Date = currentDate
    WHERE(idProject = PID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_clin` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_clin`(VID int, clinIndex VARCHAR(45), versionNumber VARCHAR(45),  projectType VARCHAR(45), clinDescription VARCHAR(1000), popStart DATE, popEnd DATE)
BEGIN
	#Creates new CLIN and sets @CLINID to most recently created CLIN's id.
    INSERT INTO CLIN (idProjectVersion, CLIN_Index)
    VALUE (VID, clinIndex);
    SET @CLINID = LAST_INSERT_ID();
    
    #Creates initial version for new CLIN
	INSERT INTO CLINVersion (idCLIN, CLIN_Index, Version_Number, Project_Type, CLIN_Description, PoP_Start, PoP_End) 
    VALUE (@CLINID, clinIndex, versionNumber, projectType, clinDescription, popStart, popEnd);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_new_project` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_new_project`(versionNumber DOUBLE, projectName VARCHAR(45), projectManager VARCHAR(45), proposalNumber INT, popStart DATE, popEnd DATE)
BEGIN
	#Inserts a new project and initial project version
	INSERT INTO Project (Project_Name) VALUE (projectName);
    
    #PID is set to newly created Project's idProject field
	SET @PID = LAST_INSERT_ID();
	INSERT INTO ProjectVersion (idProject, Version_Number, Project_Name, Project_Manager, Proposal_Number, PoP_Start, PoP_End)
    VALUES (@PID, versionNumber, projectName, projectManager, proposalNumber, popStart, popEnd);
    
    #VID is set to newly created ProjectVersion's idProjectVersion field
    SET @VID = LAST_INSERT_ID();
    SELECT * FROM ProjectVersion WHERE idProjectVersion = @VID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_organization` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_organization`(CLINVID int, orgName VARCHAR(45), versionNumber VARCHAR(45), product VARCHAR(100))
BEGIN
	#Creates new Organization associated with given CLINVersion
	INSERT INTO `Organization` (idCLINVersion, Organization_Name)
    VALUES (CLINVID, orgName);
    
	#ORGID is set to newly created Organization's idOrganization field
    SET @ORGID = LAST_INSERT_ID();
    
	INSERT INTO `OrganizationVersion` (idOrganization, Organization_Name, Version_Number, Product)
    VALUES (@ORGID, orgName, versionNumber, product);
    
    SET @ORGVID = LAST_INSERT_ID();
    
    SELECT * FROM `OrganizationVersion` WHERE idOrganizationVersion = @ORGVID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_sdrl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_sdrl`(VID int, sdrlTitle VARCHAR(45), versionNumber VARCHAR(45), sdrlDescription VARCHAR(1000))
BEGIN
	#Creates new SDRL associated with given project version
    INSERT INTO SDRL (idProjectVersion, SDRL_Title)
    VALUE (VID, sdrlTitle);
    SET @SDRLID = LAST_INSERT_ID();
    
    #Creates initial version for new SDRL
	INSERT INTO SDRLVersion (idSDRL, SDRL_Title, Version_Number, SDRL_Description) 
    VALUE (@SDRLID, sdrlTitle, versionNumber, sdrlDescription);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_sow` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_sow`(VID int, sowRef VARCHAR(45), versionNumber VARCHAR(45), sowDescription VARCHAR(1000))
BEGIN
	#Creates new SoW associated with given project version
	INSERT INTO SoW (idProjectVersion, Reference_Number)
    VALUE (VID, sowRef);
    SET @SoWID = LAST_INSERT_ID();
	
	#Creates initial version for new SoW
	INSERT INTO SoWVersion (idSoW, Reference_Number, Version_Number, SoW_Description) 
    VALUE (@SoWID, sowRef, versionNumber, sowDescription);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_task` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_task`(WPVID int, taskName varchar(45), versionNumber VARCHAR(45), estimateFormula varchar(45), staffHours varchar(45), taskDetails varchar(1000), assumptions varchar(1000), methodology varchar(1000), popStart DATE, popEnd DATE)
BEGIN
	#Creates new task associated with specified WP Version
	INSERT INTO Task (idWPVersion, Task_Name)
    VALUES (WPVID, taskName);
    
	#TASKID is set to newly created Task's idTask field
    SET @TASKID = LAST_INSERT_ID();
    
    #Creates initial version for new Task
    INSERT INTO TaskVersion (idTask, Task_Name, Version_Number, Estimate_Formula, Staff_Hours, Task_Details, Assumptions, Estimate_Methodology, PoP_Start, PoP_End)
    VALUES (@TASKID, taskName, versionNumber, estimateFormula, staffHours, taskDetails, assumptions, methodology, popStart, popEnd);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_wp` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_wp`(ORGVID int, versionNumber varchar(45), wpName varchar(45), boeAuthor varchar(45), scope varchar(45), wpType varchar(45), typeOfWork varchar(45), popStart DATE, popEnd DATE)
BEGIN
	#Creates new WP assoicated with specified Organization Version
    INSERT INTO WP (idOrganizationVersion, WP_Name)
    VALUES (ORGVID, wpName);
    
	#WPID is set to newly created WP's idWP field
    SET @WPID = LAST_INSERT_ID();
    
    #Creates initial version for new WP
	INSERT INTO WPVersion (idWP, Version_Number, WP_Name, BoE_Author, Scope, WP_Type, Type_of_Work, PoP_Start, PoP_End)
    VALUES (@WPID, versionNumber, wpName, boeAuthor, scope, wpType, typeOfWork, popStart, popEnd);

	SET @WPVID = LAST_INSERT_ID();

    SELECT * FROM WPVersion WHERE idWPVersion = @WPVID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `return_for_estimation` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `return_for_estimation`(PID INT)
BEGIN
	#Returns a project from the estimated list by setting Estimated_Date to NULL
	UPDATE Project SET
    Estimated_Date = NULL
    WHERE(idProject = PID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `return_from_approved` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `return_from_approved`(PID INT)
BEGIN
	#Returns a project from the approved list by setting Approved_Date to NULL
	UPDATE Project SET
    Approved_Date = null
    WHERE(idProject = PID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `return_from_denied` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `return_from_denied`(PID INT)
BEGIN
	#Returns a project from the denied list by setting Denied_Date to NULL
	UPDATE Project SET
    Denied_Date = null
    WHERE (idProject = PID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `return_project` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `return_project`(PID int)
BEGIN
	#Returns a project from submitted list by setting Submit_Date to null.
	UPDATE Project SET 
    Submit_Date = NULL
	WHERE (idProject = PID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `select_all_versions` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_all_versions`(PID int)
BEGIN
	SELECT * FROM ProjectVersion
    WHERE (idProject = PID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `select_clins` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_clins`(CLINID int)
BEGIN
	#Selects all Versions for a given CLIN
	SELECT * FROM CLINVersion
	WHERE idCLIN = CLINID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `select_final_status` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_final_status`(PID int)
BEGIN
	IF (SELECT Approved_Date FROM Project WHERE (idProject = PID) IS NOT NULL) THEN
		SELECT Approved_Date FROM Project WHERE (idProject = PID);
	ELSEIF (SELECT Denied_Date FROM Project WHERE (idProject = PID) IS NOT NULL) THEN
		SELECT Denied_Date FROM Project WHERE (idProject = PID);
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `select_organizations` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_organizations`(ORGID int)
BEGIN
	#Selects all Organization Versions for a given Organization
	SELECT * FROM OrganizationVersion
	WHERE idOrganization = ORGID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `select_projectVersions` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_projectVersions`(PID int)
BEGIN
	#Selects all Versions for a given project
	SELECT * FROM ProjectVersion
    WHERE (idProject = PID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `select_sdrls` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_sdrls`(SDRLID int)
BEGIN
	#Selects all SDRLs for a given projectVersion
	SELECT * FROM SDRLVersion
	WHERE idSDRL = SDRLID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `select_sows` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_sows`(SOWID int)
BEGIN
	#Selects all SoWs for a given projectVersion
	SELECT * FROM SoWVersion
	WHERE idSoW = SOWID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `select_tasks` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_tasks`(TASKID int)
BEGIN
	#Selects all Versions for a given Task
	SELECT * FROM TaskVersion
	WHERE idTask = TASKID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `select_wps` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_wps`(WPID int)
BEGIN
	#Selects all WPs for a given WPVersion
	SELECT * FROM WPVersion
	WHERE idWP = WPID;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `submit_project` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `submit_project`(PID INT, currentDate DATE)
BEGIN
	#Sets specified Project Submit_Date to today's date.
	UPDATE Project SET
    Submit_Date = currentDate
    WHERE(idProject = PID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_clin` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_clin`(CLINVID int, CLINID int, clinIndex VARCHAR(45), versionNumber VARCHAR(45),  projectType VARCHAR(45), clinDescription VARCHAR(1000), popStart DATE, popEnd DATE)
BEGIN
	#store CLINs current version number for comparison
	SELECT Version_Number FROM CLINVersion
    WHERE (idCLINVersion = CLINVID) INTO @versionNum;
	
    #check if version number was changed
    IF @versionNum = versionNumber THEN
		#if version number was not changed then just update clin row
		UPDATE CLINVersion SET 
		CLIN_Index = clinIndex,
        Project_Type = projectType,
        CLIN_Description = clinDescription,
		PoP_Start = popStart,
		PoP_End = popEnd 
		WHERE (idCLINVersion = CLINVID);
    ELSE
		#if version number was changed create new CLIN with Based_On set to old CLIN's id
		INSERT INTO CLINVersion (idCLIN, CLIN_Index, Project_Type, Version_Number, CLIN_Description, PoP_Start, PoP_End, Based_On) 
        VALUE (CLINID, clinIndex, projectType, versionNumber, clinDescription, popStart, popEnd, CLINVID);
		SET @CLINVID = LAST_INSERT_ID();
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_organization` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_organization`(ORGVID int, ORGID int, orgName VARCHAR(45), versionNumber VARCHAR(45), product VARCHAR(100))
BEGIN
	#store Orgnanization's current version number for comparison
	SELECT Version_Number FROM `OrganizationVersion`
    WHERE (idOrganizationVersion = ORGVID) INTO @versionNum;
    
    #check if version number was changed
    IF @versionNum = versionNumber THEN
		#if version number was not changed then just update organization
		UPDATE `OrganizationVersion` SET
		Organization_Name = orgName,
        Product = product
		WHERE (idOrganizationVersion = ORGVID);
        SELECT * FROM `OrganizationVersion` WHERE idOrganizationVersion = ORGVID;
	ELSE 
		#if version number was changed create new Organization Version with Based_On set to old Version's id
		INSERT INTO `OrganizationVersion` (idOrganization, Organization_Name, Version_Number, Product, Based_On) 
        VALUE (ORGID, orgName, versionNumber, product, ORGVID);
        
        #ORGID is set to newly created Organization Versions's idOrganizationVersion field
		SET @ORGVID = LAST_INSERT_ID();
        
        #Return new Organization's info to UI
        SELECT * FROM `OrganizationVersion` WHERE idOrganizationVersion = @ORGVID;
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_projectVersion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_projectVersion`(VID int, versionNumber varchar(45), projectName varchar(45), projectManager varchar(45), propNum int, popStart date, popEnd date)
BEGIN
	#Store Project id as @PID
	SELECT idProject FROM ProjectVersion
	WHERE (idProjectVersion = VID) INTO @PID;
    
    #Check if versionNumber has changed
    SELECT Version_Number FROM ProjectVersion
    WHERE (idProjectVersion = VID) INTO @versionNum;
    
    IF @versionNum = versionNumber THEN
		UPDATE ProjectVersion SET 
		Project_Name = projectName, 
		Project_Manager = projectManager,
		Proposal_Number = propNum,
		PoP_Start = popStart,
		PoP_End = popEnd 
		WHERE (idProjectVersion = VID) AND (idProject = @PID);
        SELECT * FROM ProjectVersion WHERE idProjectVersion = VID;
        
    ELSE
		#Insert new version and return new @VID for use with CLINs, SDRLs, and SoWs
        INSERT INTO ProjectVersion (idProject, Version_Number, Project_Name, Project_Manager, Proposal_Number, PoP_Start, PoP_End, Based_On)
		VALUES (@PID, versionNumber, projectName, projectManager, propNum, popStart, popEnd, VID);
		
		#VID is set to newly created ProjectVersion's idProjectVersion field
		SET @VID = LAST_INSERT_ID();
        
        #Return new project version's info to UI
		SELECT * FROM ProjectVersion WHERE idProjectVersion = @VID;
	END IF;	
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_sdrl` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_sdrl`(SDRLVID int, SDRLID int, sdrlTitle VARCHAR(45), versionNumber VARCHAR(45), sdrlDescription VARCHAR(1000))
BEGIN
	#store SDRL's current version number for comparison
	SELECT Version_Number FROM SDRLVersion
    WHERE (idSDRLVersion = SDRLVID) INTO @versionNum;
	
    #check if version number was changed
    IF @versionNum = versionNumber THEN
		#if version number was not changed then just update sdrl row
		UPDATE SDRLVersion SET 
		SDRL_Title = sdrlTitle,
        SDRL_Description = sdrlDescription
		WHERE (idSDRLVersion = SDRLVID);
    ELSE
		#if version number was changed create new SDRL with Based_On set to old SDRL version's id
		INSERT INTO SDRLVersion (idSDRL, SDRL_Title, Version_Number, SDRL_Description, Based_On) 
        VALUE (SDRLID, sdrlTitle, versionNumber, sdrlDescription, SDRLVID);
		SET @SDRLVID = LAST_INSERT_ID();
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_sow` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_sow`(SOWVID int, SOWID int, sowRef VARCHAR(45), versionNumber VARCHAR(45), sowDescription VARCHAR(1000))
BEGIN
	#store SoW's current version number for comparison
	SELECT Version_Number FROM SoWVersion
    WHERE (idSoWVersion = SOWVID) INTO @versionNum;
	
    #check if version number was changed
    IF @versionNum = versionNumber THEN
		#if version number was not changed then just update sow row
		UPDATE SoWVersion SET 
		Reference_Number = sowRef,
        Version_Number = versionNumber,
        SoW_Description = sowDescription
		WHERE (idSoWVersion = SOWVID);
    ELSE
		#if version number was changed create new SDRL with Based_On set to old SDRL's id
		INSERT INTO SoWVersion (idSoW, Reference_Number, Version_Number, SoW_Description, Based_On) 
        VALUE (SOWID, sowRef, versionNumber, sowDescription, SOWVID);
		SET @SOWVID = LAST_INSERT_ID();
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_task` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_task`(TASKVID int, TASKID int, taskName varchar(45), versionNumber varchar(45), estimateFormula varchar(45), staffHours varchar(45), taskDetails varchar(1000), assumptions varchar(1000), methodology varchar(1000), popStart DATE, popEnd DATE)
BEGIN
	#store task's current version number for comparison
	SELECT Version_Number FROM TaskVersion
    WHERE (idTaskVersion = TASKVID) INTO @versionNum;
	
    #check if version number was changed
	IF @versionNum = versionNumber THEN
		#if version number was not changed then just update task
		UPDATE TaskVersion SET
		Task_Name = taskName,
        Estimate_Formula = estimateFormula,
        Staff_Hours = staffHours,
		Task_Details = taskDetails,
		Assumptions = assumptions,
        Estimate_Methodology = methodology,
        PoP_Start = popStart,
        PoP_End = popEnd
		WHERE (idTaskVersion = TASKVID);
    ELSE
		#if version number was changed create new Task version with Based_On set to old Task version's id
		INSERT INTO TaskVersion (idTask, Task_Name, Version_Number, Estimate_Formula, Staff_Hours, Task_Details, Assumptions, Estimate_Methodology, PoP_Start, PoP_End, Based_On)
		VALUES (TASKID, taskName, versionNumber, estimateFormula, staffHours, taskDetails, assumptions, methodology, popStart, popEnd, TASKVID);
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_WP` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_WP`(WPVID int, WPID int, versionNumber varchar(45), wpName varchar(45), boeAuthor varchar(45), scope varchar(45), wpType varchar(45), typeOfWork varchar(45), popStart DATE, popEnd DATE)
BEGIN
	#store WP's current version number for comparison
	SELECT Version_Number FROM WPVersion
    WHERE (idWPVersion = WPVID) INTO @versionNum;
    
	#check if version number was changed
    IF @versionNum = versionNumber THEN
		#if version number was not changed then just update task
		UPDATE WPVersion SET
		WP_Name = wpName,
        BoE_Author = boeAuthor,
        Scope = scope,
		WP_Type = wpType,
		Type_Of_Work = typeOfWork,
        PoP_Start = popStart,
        PoP_End = popEnd
		WHERE (idWPVersion = WPVID);
        SELECT * FROM WPVersion WHERE idWPVersion = WPVID;
	ELSE
		#if version number was changed create new Task with Based_On set to old Task's id
		INSERT INTO WPVersion (idWP, Version_Number, WP_Name, BoE_Author, Scope, WP_Type, Type_of_Work, PoP_Start, PoP_End, Based_On)
		VALUES (WPID, versionNumber, wpName, boeAuthor, scope, wpType, typeOfWork, popStart, popEnd, WPVID);
        
        #WPID is set to newly created WP's idWP field
        SET @WPVID = LAST_INSERT_ID();
        
        #Return new WP's info to UI
        SELECT * FROM WPVersion WHERE idWPVersion = @WPVID;
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-22 14:39:55
