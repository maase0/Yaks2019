CREATE DEFINER=`root`@`localhost` PROCEDURE `update_WP`(WPVID int, WPID int, versionNumber varchar(45), wpName varchar(45), boeAuthor varchar(45), scope varchar(45), wpType varchar(45), typeOfWork varchar(45), popStart DATE, popEnd DATE)
BEGIN
	#store WP's current version number for comparison
	SELECT Version_Number FROM WPVersion
    WHERE (idWPVersion = WPVID) INTO @versionNum;
    
	#check if version number was changed
    IF @versionNum = versionNumber THEN
		#if version number was not changed then just update task
		UPDATE WPVersion SET
		WP_Name = wpName,
        BoE_Author = boeAuthor,
        Scope = scope,
		WP_Type = wpType,
		Type_Of_Work = typeOfWork,
        PoP_Start = popStart,
        PoP_End = popEnd
		WHERE (idWPVersion = WPVID);
        SELECT * FROM WPVersion WHERE idWPVersion = WPVID;
	ELSE
		#if version number was changed create new Task with Based_On set to old Task's id
		INSERT INTO WPVersion (idWP, Version_Number, WP_Name, BoE_Author, Scope, WP_Type, Type_of_Work, PoP_Start, PoP_End, Based_On)
		VALUES (WPID, versionNumber, wpName, boeAuthor, scope, wpType, typeOfWork, popStart, popEnd, WPVID);
        
        #WPID is set to newly created WP's idWP field
        SET @WPVID = LAST_INSERT_ID();
        
        #Return new WP's info to UI
        SELECT * FROM WPVersion WHERE idWPVersion = @WPVID;
	END IF;
END