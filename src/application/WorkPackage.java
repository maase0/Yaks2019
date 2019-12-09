package application;

import java.util.ArrayList;

public class WorkPackage {
    private String name;
    private String wptype;
    private String author;
    private String popStart;
    private String popEnd;
    private String scope;
    private String typeOfWork;
    private String version;
    private ArrayList<Task> tasks;
	private String id;
	private String oldVersion;
	private ArrayList<Task> deletedTasks = new ArrayList<Task>();
	private String versionID;

	
    public WorkPackage() {
        name = null;
        wptype = null;
        author = null;
        popStart = null;
        popEnd = null;
        scope = null;
        typeOfWork = null;
        version = null;
        tasks = new ArrayList<Task>();
        this.id = null;
        oldVersion = null;
        deletedTasks = new ArrayList<Task>();
    	versionID = null;

    }

    public WorkPackage(String name, String wptype, String author,
                       String popStart, String popEnd, String scope,
                       String worktype, String version, ArrayList<Task> tasks) {
        this.name = name;
        this.wptype = wptype;
        this.author = author;
        this.popStart = popStart;
        this.popEnd = popEnd;
        this.scope = scope;
        this.typeOfWork = worktype;
        this.version = version;
        this.oldVersion = version;
        this.tasks = tasks;
        this.id = null;
        deletedTasks = new ArrayList<Task>();
    	versionID = null;

    }
    
	public void setOldVersion(String oldVersion) {
		this.oldVersion = oldVersion;
	}
	
	public String getOldVersion() {
		return oldVersion;
	}

    public void setID(String id) {
    	this.id = id;
    }
    
    public String getID() {
    	return id;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }

    public void setWorkPackageType(String wptype) {
        this.wptype = wptype;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPopStart(String popStart) {
        this.popStart = popStart;
    }

    public void setPopEnd(String popEnd) {
        this.popEnd = popEnd;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setTypeOfWork(String worktype) {
        this.typeOfWork = worktype;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getWorkPackageType() {
        return wptype;
    }

    public String getAuthor() {
        return author;
    }

    public String getPopStart() {
        return popStart;
    }

    public String getPopEnd() {
        return popEnd;
    }

    public String getScope() {
        return scope;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }

    public String getVersion() {
        return version;
    }

    public void setTasks(ArrayList<Task> tasks) {
    	this.tasks = tasks;
    }
    
    public void addTask(Task task) {
    	tasks.add(task);
    }
    
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    
    public void setDeletedTasks(ArrayList<Task> tasks) {
    	this.deletedTasks = tasks;
    }
    
    public ArrayList<Task> getDeletedTasks() {
    	return deletedTasks;
    }
    public String toString() {
    	return name + ": " + author + ", " + wptype;
    }
    
    public String getVersionID() {
        return versionID;
    }
    
    public void setVersionID(String versionID) {
        this.versionID = versionID;
    }
    
}