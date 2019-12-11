CREATE DEFINER=`root`@`localhost` PROCEDURE `return_from_denied`(PID INT)
BEGIN
	#Returns a project from the denied list by setting Denied_Date to NULL
	UPDATE Project SET
    Denied_Date = null
    WHERE (idProject = PID);
END