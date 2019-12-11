CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_organization`(ORGID int)
BEGIN
	#Deletes specified Organization which then cascades to associated tasks
	DELETE FROM `Organization` WHERE (idOrganization = ORGID);
END