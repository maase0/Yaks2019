CREATE DEFINER=`root`@`localhost` PROCEDURE `clone_organization`(CLINVID int, ORGVID int, orgName VARCHAR(45), versionNumber VARCHAR(45), product VARCHAR(100))
BEGIN
	#used with update_projectVersion or clin when the version number changes to clone old version's Organizations
    SELECT idOrganization FROM OrganizationVersion 
    WHERE (idOrganizationVersion = ORGVID) INTO @ORGID;
    
    INSERT INTO `Organization` (idCLINVersion, Organization_Name, Based_On)
    VALUES (CLINVID, orgName, @ORGID);
    
    SET @ORGID = LAST_INSERT_ID();
    
	INSERT INTO `OrganizationVersion` (idOrganization, Organization_Name, Version_Number, Product, Based_On)
    VALUES (@ORGID, orgName, versionNumber, product, ORGVID);
    
    SET @ORGVID = LAST_INSERT_ID();
    SELECT * FROM `OrganizationVersion` WHERE idOrganizationVersion = @ORGVID;
END