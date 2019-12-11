CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_sdrl`(VID int, sdrlTitle VARCHAR(45), versionNumber VARCHAR(45), sdrlDescription VARCHAR(1000))
BEGIN
	#Creates new SDRL associated with given project version
    INSERT INTO SDRL (idProjectVersion, SDRL_Title)
    VALUE (VID, sdrlTitle);
    SET @SDRLID = LAST_INSERT_ID();
    
    #Creates initial version for new SDRL
	INSERT INTO SDRLVersion (idSDRL, SDRL_Title, Version_Number, SDRL_Description) 
    VALUE (@SDRLID, sdrlTitle, versionNumber, sdrlDescription);
END