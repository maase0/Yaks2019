CREATE DEFINER=`root`@`localhost` PROCEDURE `clone_wp`(ORGVID int, WPVID int, versionNumber varchar(45), wpName varchar(45), boeAuthor varchar(45), scope varchar(45), wpType varchar(45), typeOfWork varchar(45), popStart DATE, popEnd DATE)
BEGIN
	#used with update_projectVersion or Organization when the version number changes to clone old version's WP
    SELECT idWP FROM WPVersion 
    WHERE (idWPVersion = WPVID) INTO @WPID;
    
    INSERT INTO WP (idOrganizationVersion, WP_Name, Based_On)
    VALUES (ORGVID, wpName, @WPID);
    
    SET @WPID = LAST_INSERT_ID();
    
	INSERT INTO WPVersion (idWP, Version_Number, WP_Name, BoE_Author, Scope, WP_Type, Type_of_Work, PoP_Start, PoP_End, Based_On)
    VALUES (@WPID, versionNumber, wpName, boeAuthor, scope, wpType, typeOfWork, popStart, popEnd, WPVID);

	SET @WPVID = LAST_INSERT_ID();

    SELECT * FROM WPVersion WHERE idWPVersion = @WPVID;
END