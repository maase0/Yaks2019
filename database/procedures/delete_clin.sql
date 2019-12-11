CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_clin`(CLINID int)
BEGIN
	#Deletes specified CLIN which then cascades to associated orgs to work packages to tasks.
	DELETE FROM CLIN WHERE (idCLIN = CLINID);
END