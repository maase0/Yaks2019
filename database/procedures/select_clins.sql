CREATE DEFINER=`root`@`localhost` PROCEDURE `select_clins`(CLINID int)
BEGIN
	#Selects all Versions for a given CLIN
	SELECT * FROM CLINVersion
	WHERE idCLIN = CLINID;
END