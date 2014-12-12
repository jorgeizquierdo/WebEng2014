package toDo.common;

public class ToDo {

	private String task;
	private String context;
	private String project;
	private int priority;

	public ToDo(String task, String context, String project, int priority) {
		this.task = task;
		this.context = context;
		this.project = project;
		this.priority = priority;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int integer) {
		this.priority = integer;
	}
}
