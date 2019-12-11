CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_task`(TASKID int)
BEGIN
	#Deletes specified task.
	DELETE FROM Task WHERE (idTask = TASKID);
END