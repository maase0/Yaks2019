CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_clin`(VID int, clinIndex VARCHAR(45), versionNumber VARCHAR(45),  projectType VARCHAR(45), clinDescription VARCHAR(1000), popStart DATE, popEnd DATE)
BEGIN
	#Creates new CLIN and sets @CLINID to most recently created CLIN's id.
    INSERT INTO CLIN (idProjectVersion, CLIN_Index)
    VALUE (VID, clinIndex);
    SET @CLINID = LAST_INSERT_ID();
    
    #Creates initial version for new CLIN
	INSERT INTO CLINVersion (idCLIN, CLIN_Index, Version_Number, Project_Type, CLIN_Description, PoP_Start, PoP_End) 
    VALUE (@CLINID, clinIndex, versionNumber, projectType, clinDescription, popStart, popEnd);
END