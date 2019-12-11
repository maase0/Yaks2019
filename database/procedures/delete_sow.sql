CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_sow`(SoWID int)
BEGIN
	#Deletes specified SoW.
	DELETE FROM SoW WHERE (idSoW = SoWID);
END