CREATE DEFINER=`root`@`localhost` PROCEDURE `select_all_versions`(PID int)
BEGIN
	SELECT * FROM ProjectVersion
    WHERE (idProject = PID);
END