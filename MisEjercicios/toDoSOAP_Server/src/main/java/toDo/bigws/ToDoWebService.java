package toDo.bigws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import toDo.common.ToDo;
import toDo.common.ToDoList;

@WebService
public class ToDoWebService {
	
	@WebMethod()
	public String addToDo(String task, String context, String project, int priority) {
		
		try{
			ToDoList.addToDo(new ToDo(task, context, project, priority));
			return "Task sucessfully added";
		}
		catch(Exception ex){
			return ex.getMessage();
		}
	}
	
	@WebMethod()
	public String removeToDo(String name) {
		try {
			if(ToDoList.removeToDo(name)){
				return "Task sucessfully deleted";
			}
			else{
				return "Task not sucessfully deleted";
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@WebMethod()
	public List<ToDo> listToDo() {
		return ToDoList.getToDoList();
	}
	
	@WebMethod()
	public List<ToDo> listToDoFilter(String task, String context, String project, int priority) {
		return ToDoList.getToDoList(task, context, project, String.valueOf(priority));
	}
}
