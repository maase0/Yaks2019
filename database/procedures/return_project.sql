CREATE DEFINER=`root`@`localhost` PROCEDURE `return_project`(PID int)
BEGIN
	#Returns a project from submitted list by setting Submit_Date to null.
	UPDATE Project SET 
    Submit_Date = NULL
	WHERE (idProject = PID);
END