CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_wp`(ORGVID int, versionNumber varchar(45), wpName varchar(45), boeAuthor varchar(45), scope varchar(45), wpType varchar(45), typeOfWork varchar(45), popStart DATE, popEnd DATE)
BEGIN
	#Creates new WP assoicated with specified Organization Version
    INSERT INTO WP (idOrganizationVersion, WP_Name)
    VALUES (ORGVID, wpName);
    
	#WPID is set to newly created WP's idWP field
    SET @WPID = LAST_INSERT_ID();
    
    #Creates initial version for new WP
	INSERT INTO WPVersion (idWP, Version_Number, WP_Name, BoE_Author, Scope, WP_Type, Type_of_Work, PoP_Start, PoP_End)
    VALUES (@WPID, versionNumber, wpName, boeAuthor, scope, wpType, typeOfWork, popStart, popEnd);

	SET @WPVID = LAST_INSERT_ID();

    SELECT * FROM WPVersion WHERE idWPVersion = @WPVID;
END