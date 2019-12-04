package application;

public class Task {
	private String name;
	private String popStart;
	private String popEnd;
	private String formula;
	private int staffHours;
	private String version;
	private String details;
	private String conditions;
	private String methodology;
	private String id;
	private String oldVersion;

	public Task() {
		name = null;
		popStart = null;
		popEnd = null;
		formula = null;
		staffHours = 0;
		version = null;
		details = null;
		conditions = null;
		methodology = null;
		id = null;
		oldVersion = null;
	}

	public Task(String name, String popStart, String popEnd, String formula, int staffHours, String version,
			String details, String conditions, String methodology) {
		this.name = name;
		this.popStart = popStart;
		this.popEnd = popEnd;
		this.formula = formula;
		this.staffHours = staffHours;
		this.version = version;
		this.oldVersion = version;
		this.details = details;
		this.conditions = conditions;
		this.methodology = methodology;
		this.id = null;
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

	public void setPopStart(String popStart) {
		this.popStart = popStart;
	}

	public void setPopEnd(String popEnd) {
		this.popEnd = popEnd;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void setStaffHours(int staffHours) {
		this.staffHours = staffHours;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public void setMethodology(String methodology) {
		this.methodology = methodology;
	}

	public String getName() {
		return name;
	}

	public String getPopStart() {
		return popStart;
	}

	public String getPopEnd() {
		return popEnd;
	}

	public String getFormula() {
		return formula;
	}

	public String getVersion() {
		return version;
	}

	public String getDetails() {
		return details;
	}

	public String getConditions() {
		return conditions;
	}

	public String getMethodology() {
		return methodology;
	}

	public int getStaffHours() {
		return staffHours;
	}

}
