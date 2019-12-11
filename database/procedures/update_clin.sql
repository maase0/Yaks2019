CREATE DEFINER=`root`@`localhost` PROCEDURE `update_clin`(CLINVID int, CLINID int, clinIndex VARCHAR(45), versionNumber VARCHAR(45),  projectType VARCHAR(45), clinDescription VARCHAR(1000), popStart DATE, popEnd DATE)
BEGIN
	#store CLINs current version number for comparison
	SELECT Version_Number FROM CLINVersion
    WHERE (idCLINVersion = CLINVID) INTO @versionNum;
	
    #check if version number was changed
    IF @versionNum = versionNumber THEN
		#if version number was not changed then just update clin row
		UPDATE CLINVersion SET 
		CLIN_Index = clinIndex,
        Project_Type = projectType,
        CLIN_Description = clinDescription,
		PoP_Start = popStart,
		PoP_End = popEnd 
		WHERE (idCLINVersion = CLINVID);
    ELSE
		#if version number was changed create new CLIN with Based_On set to old CLIN's id
		INSERT INTO CLINVersion (idCLIN, CLIN_Index, Project_Type, Version_Number, CLIN_Description, PoP_Start, PoP_End, Based_On) 
        VALUE (CLINID, clinIndex, projectType, versionNumber, clinDescription, popStart, popEnd, CLINVID);
		SET @CLINVID = LAST_INSERT_ID();
	END IF;
END