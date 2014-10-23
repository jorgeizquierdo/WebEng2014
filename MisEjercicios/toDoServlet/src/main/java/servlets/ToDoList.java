package servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class ToDoList {

	private final static String DEFAULT_FILE_NAME = "toDo_book.json";

	private List<ToDo> toDoList;
	
	private ToDoList(){
		this.toDoList = new ArrayList<ToDo>();
	}
	
	public static synchronized void addToDo(ToDo toDo) throws Exception {
		ToDoList toDoList;
		Gson gson = new Gson();
		try {
			toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME), ToDoList.class);
		} catch (FileNotFoundException e) {
			toDoList = new ToDoList();
		}
		
		toDoList.toDoList.add(toDo);		
		
		FileWriter output = new FileWriter(DEFAULT_FILE_NAME);
		output.write(gson.toJson(toDoList));
		output.close();
	}
	
	public static List<ToDo> getToDoList(){
		ToDoList toDoList;
		Gson gson = new Gson();
		try {
			toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME), ToDoList.class);
		} catch (FileNotFoundException e) {
			toDoList = new ToDoList();
		}
		
		return toDoList.toDoList;
	}
	
	public static List<ToDo> getToDoList(String task, String context, String project, String priority){
		ToDoList toDoList;
		Gson gson = new Gson();
		try {
			toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME), ToDoList.class);
		} catch (FileNotFoundException e) {
			toDoList = new ToDoList();
		}
		
		task = task == "" ? null : task;
		context = context == "" ? null : context;
		project = project == "" ? null : project;
		priority = priority == "" ? null : priority;
		
		if(task == null && context == null && project == null && priority == null){
			return toDoList.toDoList;
		}
		else{
			List<ToDo> filterList = new ArrayList<ToDo>();
			for(ToDo toDo : toDoList.toDoList){
				if( (task == null || (task != null && toDo.getTask().toLowerCase().contains(task.toLowerCase()))) 
					&& (context == null || (context != null && toDo.getContext().toLowerCase().contains(context.toLowerCase()))) 
					&& (project == null || (project != null && toDo.getProject().toLowerCase().contains(project.toLowerCase()))) 
					&& (priority == null || (priority != null && String.valueOf(toDo.getPriority()).contains(priority.toLowerCase()))) ){
					filterList.add(toDo);
				}
			}
			return filterList;
		}
	}
}
