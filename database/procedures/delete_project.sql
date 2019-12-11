CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_project`(PID int)
BEGIN
	#Deletes specified project then cascades to everything else.
	DELETE FROM Project WHERE (idProject = PID);
END