package todo.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;

public class ToDoList {

	private final static String DEFAULT_FILE_NAME = "toDo_book.json";
	private final static Gson gson = new Gson();

	private List<ToDo> toDoList;
	
	private ToDoList(){
		this.toDoList = new ArrayList<ToDo>();
	}
	
	public static synchronized void addToDo(ToDo toDo) throws IOException {
		ToDoList objToDoList = readToDoList();
		
		int id = objToDoList.toDoList != null && objToDoList.toDoList.size() > 0 ? objToDoList.toDoList.get(objToDoList.toDoList.size() - 1).getId() + 1 : 1;
		toDo.setId(id);
		
		objToDoList.toDoList.add(toDo);	
		
		saveToDoList(objToDoList);
	}
	
	public static synchronized boolean updateToDo(int id, ToDo toDo) throws IOException{
		ToDoList objToDoList = readToDoList();
		
		boolean update = false;		
		for(int i = 0; i < objToDoList.toDoList.size() && !update; i++){
			if(update = objToDoList.toDoList.get(i).getId() == id){
				toDo.setId(id);
				toDo.setHref(objToDoList.toDoList.get(i).getHref());
				objToDoList.toDoList.set(i, toDo);
			}
		}
		
		saveToDoList(objToDoList);
		
		return update;
	}
	
 	public static synchronized boolean removeToDo(int id) throws IOException {
		ToDoList objToDoList = readToDoList();
		
		boolean remove = false;
		for(Iterator<ToDo> it = objToDoList.toDoList.iterator(); it.hasNext() && !remove; ){
			if(remove = it.next().getId() == id){
				it.remove();
			}
		}
		
		saveToDoList(objToDoList);
		
		return remove;
	}
	
	public static List<ToDo> getToDoList(){
		return readToDoList().toDoList;
	}
	
	public static List<ToDo> getToDoList(String task, String context, String project, int priority){
		ToDoList objToDoList = readToDoList();
		
		task = task == "" ? null : task;
		context = context == "" ? null : context;
		project = project == "" ? null : project;
		
		if(task == null && context == null && project == null && priority < 0){
			return objToDoList.toDoList;
		}
		else{
			List<ToDo> filterList = new ArrayList<ToDo>();
			for(ToDo toDo : objToDoList.toDoList){
				if( (task == null || (task != null && toDo.getTask().toLowerCase().contains(task.toLowerCase()))) 
					&& (context == null || (context != null && toDo.getContext().toLowerCase().contains(context.toLowerCase()))) 
					&& (project == null || (project != null && toDo.getProject().toLowerCase().contains(project.toLowerCase()))) 
					&& (priority < 0 || priority == toDo.getPriority()) ){
					filterList.add(toDo);
				}
			}
			return filterList;
		}
	}

	private static ToDoList readToDoList(){
		ToDoList objToDoList;
		try {
			objToDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME), ToDoList.class);
		} catch (FileNotFoundException e) {
			objToDoList = new ToDoList();
		}
		return objToDoList;
	}
	
	private static void saveToDoList(ToDoList objToDoList) throws IOException{
		FileWriter output = new FileWriter(DEFAULT_FILE_NAME);
		output.write(gson.toJson(objToDoList));
		output.close();		
	}
}
