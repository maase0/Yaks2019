CREATE DEFINER=`root`@`localhost` PROCEDURE `select_projectVersions`(PID int)
BEGIN
	#Selects all Versions for a given project
	SELECT * FROM ProjectVersion
    WHERE (idProject = PID);
END