CREATE DEFINER=`root`@`localhost` PROCEDURE `select_organizations`(ORGID int)
BEGIN
	#Selects all Organization Versions for a given Organization
	SELECT * FROM OrganizationVersion
	WHERE idOrganization = ORGID;
END