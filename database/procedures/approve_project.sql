CREATE DEFINER=`root`@`localhost` PROCEDURE `approve_project`(PID INT, currentDate DATE)
BEGIN
    #Check if project is already denied, UI should not allow this but regardless.
    #Then set Approved_Date to today's date.
	UPDATE Project SET
    Approved_Date = currentDate
    WHERE (idProject = PID AND Denied_Date IS NULL);
END