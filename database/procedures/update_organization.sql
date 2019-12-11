CREATE DEFINER=`root`@`localhost` PROCEDURE `update_organization`(ORGVID int, ORGID int, orgName VARCHAR(45), versionNumber VARCHAR(45), product VARCHAR(100))
BEGIN
	#store Orgnanization's current version number for comparison
	SELECT Version_Number FROM `OrganizationVersion`
    WHERE (idOrganizationVersion = ORGVID) INTO @versionNum;
    
    #check if version number was changed
    IF @versionNum = versionNumber THEN
		#if version number was not changed then just update organization
		UPDATE `OrganizationVersion` SET
		Organization_Name = orgName,
        Product = product
		WHERE (idOrganizationVersion = ORGVID);
        SELECT * FROM `OrganizationVersion` WHERE idOrganizationVersion = ORGVID;
	ELSE 
		#if version number was changed create new Organization Version with Based_On set to old Version's id
		INSERT INTO `OrganizationVersion` (idOrganization, Organization_Name, Version_Number, Product, Based_On) 
        VALUE (ORGID, orgName, versionNumber, product, ORGVID);
        
        #ORGID is set to newly created Organization Versions's idOrganizationVersion field
		SET @ORGVID = LAST_INSERT_ID();
        
        #Return new Organization's info to UI
        SELECT * FROM `OrganizationVersion` WHERE idOrganizationVersion = @ORGVID;
	END IF;
END