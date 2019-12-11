CREATE DEFINER=`root`@`localhost` PROCEDURE `clone_sdrl`(VID int, SDRLVID int, sdrlTitle VARCHAR(45), versionNumber VARCHAR(45), sdrlDescription VARCHAR(1000))
BEGIN
	#used with update_projectVersion when the version number changes to clone old version's SDRLs
	SELECT idSDRL FROM SDRLVersion 
    WHERE idSDRLVersion = SDRLVID INTO @SDRLID;
    
    INSERT INTO SDRL (idProjectVersion, SDRL_Title, Based_On)
    VALUE (VID, sdrlTitle, @SDRLID);
    
    SET @SDRLID = LAST_INSERT_ID();
    
	INSERT INTO SDRLVersion (idSDRL, SDRL_Title, Version_Number, SDRL_Description, Based_On) 
    VALUE (@SDRLID, sdrlTitle, versionNumber, sdrlDescription, SDRLVID);
END