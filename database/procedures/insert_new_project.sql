CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_new_project`(versionNumber DOUBLE, projectName VARCHAR(45), projectManager VARCHAR(45), proposalNumber INT, popStart DATE, popEnd DATE)
BEGIN
	#Inserts a new project and initial project version
	INSERT INTO Project (Project_Name) VALUE (projectName);
    
    #PID is set to newly created Project's idProject field
	SET @PID = LAST_INSERT_ID();
	INSERT INTO ProjectVersion (idProject, Version_Number, Project_Name, Project_Manager, Proposal_Number, PoP_Start, PoP_End)
    VALUES (@PID, versionNumber, projectName, projectManager, proposalNumber, popStart, popEnd);
    
    #VID is set to newly created ProjectVersion's idProjectVersion field
    SET @VID = LAST_INSERT_ID();
    SELECT * FROM ProjectVersion WHERE idProjectVersion = @VID;
END