CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_wp`(WPID int)
BEGIN
	#Deletes specified work package and then cascades to associated tasks.
	DELETE FROM WP WHERE (idWP = WPID);
END