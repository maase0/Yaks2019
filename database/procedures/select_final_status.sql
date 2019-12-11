CREATE DEFINER=`root`@`localhost` PROCEDURE `select_final_status`(PID int)
BEGIN
	IF (SELECT Approved_Date FROM Project WHERE (idProject = PID) IS NOT NULL) THEN
		SELECT Approved_Date FROM Project WHERE (idProject = PID);
	ELSEIF (SELECT Denied_Date FROM Project WHERE (idProject = PID) IS NOT NULL) THEN
		SELECT Denied_Date FROM Project WHERE (idProject = PID);
	END IF;
END