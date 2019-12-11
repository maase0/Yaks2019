CREATE DEFINER=`root`@`localhost` PROCEDURE `estimate_project`(PID INT, currentDate DATE)
BEGIN
	#Sets project's Estimated_Date to current date.
	UPDATE Project SET
    Estimated_Date = currentDate
    WHERE(idProject = PID);
END