CREATE DEFINER=`root`@`localhost` PROCEDURE `update_sow`(SOWVID int, SOWID int, sowRef VARCHAR(45), versionNumber VARCHAR(45), sowDescription VARCHAR(1000))
BEGIN
	#store SoW's current version number for comparison
	SELECT Version_Number FROM SoWVersion
    WHERE (idSoWVersion = SOWVID) INTO @versionNum;
	
    #check if version number was changed
    IF @versionNum = versionNumber THEN
		#if version number was not changed then just update sow row
		UPDATE SoWVersion SET 
		Reference_Number = sowRef,
        Version_Number = versionNumber,
        SoW_Description = sowDescription
		WHERE (idSoWVersion = SOWVID);
    ELSE
		#if version number was changed create new SDRL with Based_On set to old SDRL's id
		INSERT INTO SoWVersion (idSoW, Reference_Number, Version_Number, SoW_Description, Based_On) 
        VALUE (SOWID, sowRef, versionNumber, sowDescription, SOWVID);
		SET @SOWVID = LAST_INSERT_ID();
	END IF;
END