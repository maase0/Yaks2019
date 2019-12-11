CREATE DEFINER=`root`@`localhost` PROCEDURE `clone_sow`(VID int, SOWVID int, sowRef VARCHAR(45), versionNumber VARCHAR(45), sowDescription VARCHAR(1000))
BEGIN
	#used with update_projectVersion when the version number changes to clone old version's SOWs
    SELECT idSoW FROM SoWVersion 
    WHERE idSoWVersion = SOWVID INTO @SOWID;
    
    INSERT INTO SoW (idProjectVersion, Reference_Number, Based_On)
    VALUE (VID, sowRef, @SOWID);
    
    SET @SOWID = LAST_INSERT_ID();
    
	INSERT INTO SoWVersion (idSoW, Reference_Number, Version_Number, SoW_Description, Based_On)
    VALUE (@SOWID, sowRef, versionNumber, sowDescription, SOWVID);
END