CREATE DEFINER=`root`@`localhost` PROCEDURE `select_sdrls`(SDRLID int)
BEGIN
	#Selects all SDRLs for a given projectVersion
	SELECT * FROM SDRLVersion
	WHERE idSDRL = SDRLID;
END