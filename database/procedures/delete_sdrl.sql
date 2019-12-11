CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_sdrl`(SDRLID int)
BEGIN
	#Deletes specified SDRL.
	DELETE FROM SDRL WHERE (idSDRL = SDRLID);
END