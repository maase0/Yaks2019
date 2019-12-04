package application;

import java.util.ArrayList;

public class WorkPackage {
    private String name;
    private String wptype;
    private String author;
    private String popStart;
    private String popEnd;
    private String scope;
    private String worktype;
    private String version;
    private ArrayList<Task> tasks;

    public WorkPackage() {
        name = null;
        wptype = null;
        author = null;
        popStart = null;
        popEnd = null;
        scope = null;
        worktype = null;
        version = null;
        tasks = null;
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
        this.worktype = worktype;
        this.version = version;
        this.tasks = tasks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWptype(String wptype) {
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

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getWptype() {
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

    public String getWorktype() {
        return worktype;
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
    
    public String toString() {
    	return name + ": " + author + ", " + wptype;
    }
}