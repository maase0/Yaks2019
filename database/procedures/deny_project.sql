CREATE DEFINER=`root`@`localhost` PROCEDURE `deny_project`(PID INT, currentDate DATE)
BEGIN
    #Check if project is already approved, UI should not allow this but regardless.
    #Then set Denied_Date to today's date.
	UPDATE Project SET
    Denied_Date = currentDate
    WHERE (idProject = PID AND Approved_Date IS NULL);
END