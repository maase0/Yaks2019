CREATE DEFINER=`root`@`localhost` PROCEDURE `update_sdrl`(SDRLVID int, SDRLID int, sdrlTitle VARCHAR(45), versionNumber VARCHAR(45), sdrlDescription VARCHAR(1000))
BEGIN
	#store SDRL's current version number for comparison
	SELECT Version_Number FROM SDRLVersion
    WHERE (idSDRLVersion = SDRLVID) INTO @versionNum;
	
    #check if version number was changed
    IF @versionNum = versionNumber THEN
		#if version number was not changed then just update sdrl row
		UPDATE SDRLVersion SET 
		SDRL_Title = sdrlTitle,
        SDRL_Description = sdrlDescription
		WHERE (idSDRLVersion = SDRLVID);
    ELSE
		#if version number was changed create new SDRL with Based_On set to old SDRL version's id
		INSERT INTO SDRLVersion (idSDRL, SDRL_Title, Version_Number, SDRL_Description, Based_On) 
        VALUE (SDRLID, sdrlTitle, versionNumber, sdrlDescription, SDRLVID);
		SET @SDRLVID = LAST_INSERT_ID();
	END IF;
END