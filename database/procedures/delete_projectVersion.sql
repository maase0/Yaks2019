CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_projectVersion`(VID INT)
BEGIN
	#Deletes specified version of project and then cascades to everything it has.
	DELETE FROM ProjectVersion WHERE (idProjectVersion = VID);
END