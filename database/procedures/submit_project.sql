CREATE DEFINER=`root`@`localhost` PROCEDURE `submit_project`(PID INT, currentDate DATE)
BEGIN
	#Sets specified Project Submit_Date to today's date.
	UPDATE Project SET
    Submit_Date = currentDate
    WHERE(idProject = PID);
END