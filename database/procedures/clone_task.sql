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
END