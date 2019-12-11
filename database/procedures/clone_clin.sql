CREATE DEFINER=`root`@`localhost` PROCEDURE `clone_clin`(VID int, CLINVID int, clinIndex VARCHAR(45), versionNumber VARCHAR(45),  projectType VARCHAR(45), clinDescription VARCHAR(1000), popStart DATE, popEnd DATE)
BEGIN
	#used with update_projectVersion when the version number changes to clone old version's CLINs
	SELECT idCLIN FROM CLINVersion 
    WHERE idCLINVersion = CLINVID INTO @CLINID;
    
    INSERT INTO CLIN (idProjectVersion, CLIN_Index, Based_On)
    VALUE (VID, clinIndex, @CLINID);
    
    SET @CLINID = LAST_INSERT_ID();
    
	INSERT INTO CLINVersion (idCLIN, CLIN_Index, Version_Number, Project_Type, CLIN_Description, PoP_Start, PoP_End, Based_On) 
    VALUE (@CLINID, clinIndex, versionNumber, projectType, clinDescription, popStart, popEnd, CLINVID);
    
    SET @CLINVID = LAST_INSERT_ID();
    SELECT * FROM CLINVersion WHERE (idCLINVersion = @CLINVID);
END