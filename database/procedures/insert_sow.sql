CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_sow`(VID int, sowRef VARCHAR(45), versionNumber VARCHAR(45), sowDescription VARCHAR(1000))
BEGIN
	#Creates new SoW associated with given project version
	INSERT INTO SoW (idProjectVersion, Reference_Number)
    VALUE (VID, sowRef);
    SET @SoWID = LAST_INSERT_ID();
	
	#Creates initial version for new SoW
	INSERT INTO SoWVersion (idSoW, Reference_Number, Version_Number, SoW_Description) 
    VALUE (@SoWID, sowRef, versionNumber, sowDescription);
END