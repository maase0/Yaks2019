CREATE DEFINER=`root`@`localhost` PROCEDURE `select_tasks`(TASKID int)
BEGIN
	#Selects all Versions for a given Task
	SELECT * FROM TaskVersion
	WHERE idTask = TASKID;
END