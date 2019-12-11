CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_organization`(CLINVID int, orgName VARCHAR(45), versionNumber VARCHAR(45), product VARCHAR(100))
BEGIN
	#Creates new Organization associated with given CLINVersion
	INSERT INTO `Organization` (idCLINVersion, Organization_Name)
    VALUES (CLINVID, orgName);
    
	#ORGID is set to newly created Organization's idOrganization field
    SET @ORGID = LAST_INSERT_ID();
    
	INSERT INTO `OrganizationVersion` (idOrganization, Organization_Name, Version_Number, Product)
    VALUES (@ORGID, orgName, versionNumber, product);
    
    SET @ORGVID = LAST_INSERT_ID();
    
    SELECT * FROM `OrganizationVersion` WHERE idOrganizationVersion = @ORGVID;
END