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
END