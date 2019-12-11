CREATE DEFINER=`root`@`localhost` PROCEDURE `return_for_estimation`(PID INT)
BEGIN
	#Returns a project from the estimated list by setting Estimated_Date to NULL
	UPDATE Project SET
    Estimated_Date = NULL
    WHERE(idProject = PID);
END