CREATE DEFINER=`root`@`localhost` PROCEDURE `return_from_approved`(PID INT)
BEGIN
	#Returns a project from the approved list by setting Approved_Date to NULL
	UPDATE Project SET
    Approved_Date = null
    WHERE(idProject = PID);
END