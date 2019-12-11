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
END