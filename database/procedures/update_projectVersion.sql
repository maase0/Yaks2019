CREATE DEFINER=`root`@`localhost` PROCEDURE `update_projectVersion`(VID int, versionNumber varchar(45), projectName varchar(45), projectManager varchar(45), propNum int, popStart date, popEnd date)
BEGIN
	#Store Project id as @PID
	SELECT idProject FROM ProjectVersion
	WHERE (idProjectVersion = VID) INTO @PID;
    
    #Check if versionNumber has changed
    SELECT Version_Number FROM ProjectVersion
    WHERE (idProjectVersion = VID) INTO @versionNum;
    
    IF @versionNum = versionNumber THEN
		UPDATE ProjectVersion SET 
		Project_Name = projectName, 
		Project_Manager = projectManager,
		Proposal_Number = propNum,
		PoP_Start = popStart,
		PoP_End = popEnd 
		WHERE (idProjectVersion = VID) AND (idProject = @PID);
        SELECT * FROM ProjectVersion WHERE idProjectVersion = VID;
        
    ELSE
		#Insert new version and return new @VID for use with CLINs, SDRLs, and SoWs
        INSERT INTO ProjectVersion (idProject, Version_Number, Project_Name, Project_Manager, Proposal_Number, PoP_Start, PoP_End, Based_On)
		VALUES (@PID, versionNumber, projectName, projectManager, propNum, popStart, popEnd, VID);
		
		#VID is set to newly created ProjectVersion's idProjectVersion field
		SET @VID = LAST_INSERT_ID();
        
        #Return new project version's info to UI
		SELECT * FROM ProjectVersion WHERE idProjectVersion = @VID;
	END IF;	
    
END