CREATE DEFINER=`root`@`localhost` PROCEDURE `check_status`(PID int)
BEGIN
	#Checks approved/denied status for an estimated project. Returns result in @state variable.
	SELECT Estimated_Date FROM Project 
    WHERE (idProject = PID) INTO @estimated;
    
    SELECT Approved_Date FROM Project
    WHERE (idProject = PID) INTO @approved;
    
    SELECT Denied_Date FROM Project
    WHERE (idProject = PID) INTO @denied;
    
	IF (@approved IS NOT NULL) THEN
		SET @state = "Approved";
    ELSEIF (@denied IS NOT NULL) THEN
		SET @state = "Denied";
	ELSEIF (@estimated IS NOT NULL AND @approved IS NULL AND @denied IS NULL) THEN
		SET @state = "Pending";
	ELSE
		SET @state = NULL;
    END IF;
    
    SELECT @state AS `status`;
END