package formats.json;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {

	private List<ToDo> toDoList = new ArrayList<ToDo>();

	public List<ToDo> getToDoList() {
		return toDoList;
	}

	public void setToDoList(List<ToDo> toDos) {
		this.toDoList = toDos;
	}

	public void addToDo(ToDo toDo) {
		toDoList.add(toDo);
	}
}
