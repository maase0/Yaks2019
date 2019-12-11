CREATE DEFINER=`root`@`localhost` PROCEDURE `select_sows`(SOWID int)
BEGIN
	#Selects all SoWs for a given projectVersion
	SELECT * FROM SoWVersion
	WHERE idSoW = SOWID;
END