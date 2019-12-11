CREATE DEFINER=`root`@`localhost` PROCEDURE `select_wps`(WPID int)
BEGIN
	#Selects all WPs for a given WPVersion
	SELECT * FROM WPVersion
	WHERE idWP = WPID;
END