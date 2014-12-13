package todo.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

public class ToDoList {

	private final static String DEFAULT_FILE_NAME = "toDo_book.json";
	private final static Gson gson = new Gson();

	private List<ToDo> toDoList;
	
	public ToDoList(){
		this.toDoList = new ArrayList<ToDo>();
	}
	
	public synchronized void addToDo(ToDo toDo) throws IOException {
		updateToDoList();
		
		int id = toDoList != null && toDoList.size() > 0 ? toDoList.get(toDoList.size() - 1).getId() + 1 : 1;
		toDo.setId(id);
		
		toDoList.add(toDo);	
		
		saveToDoList();
	}
	
	public synchronized void addToDo(UriInfo info, ToDo toDo) throws Exception {
		updateToDoList();
		
		int id = toDoList != null && toDoList.size() > 0 ? toDoList.get(toDoList.size() - 1).getId() + 1 : 1;
		toDo.setId(id);
		toDo.setHref(info.getAbsolutePathBuilder().path("/{id}").build(toDo.getId()));
		
		toDoList.add(toDo);	
		
		saveToDoList();
	}
	
	public synchronized boolean updateToDo(int id, ToDo toDo) throws IOException{
		updateToDoList();
		
		boolean update = false;		
		for(int i = 0; i < toDoList.size() && !update; i++){
			if(update = toDoList.get(i).getId() == id){
				toDo.setId(id);
				toDo.setHref(toDoList.get(i).getHref());
				toDoList.set(i, toDo);
			}
		}
		
		saveToDoList();
		
		return update;
	}
	
 	public synchronized boolean removeToDo(int id) throws IOException {
		updateToDoList();
		
		boolean remove = false;
		for(Iterator<ToDo> it = toDoList.iterator(); it.hasNext() && !remove; ){
			if(remove = it.next().getId() == id){
				it.remove();
			}
		}
		
		saveToDoList();
		
		return remove;
	}
	
	public List<ToDo> getToDoList(){	
		updateToDoList();
		
		return toDoList;
	}
	
	public List<ToDo> getToDoList(String task, String context, String project, String priority){
		updateToDoList();
		
		task = task == "" ? null : task;
		context = context == "" ? null : context;
		project = project == "" ? null : project;
		priority = (priority == "" || Integer.parseInt(priority) < 0) ? null : priority;
		
		if(task == null && context == null && project == null && priority == null){
			return toDoList;
		}
		else{
			List<ToDo> filterList = new ArrayList<ToDo>();
			for(ToDo toDo : toDoList){
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

	public void updateToDoList(){
		try {
			toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME), ToDoList.class).toDoList;
		} catch (FileNotFoundException e) {
			toDoList = new ArrayList<ToDo>();
		}
	}
	
	public void saveToDoList() throws IOException{
		FileWriter output = new FileWriter(DEFAULT_FILE_NAME);
		output.write(gson.toJson(this));
		output.close();		
	}
}
