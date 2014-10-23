package formats.json;

import java.io.FileReader;

import com.google.gson.Gson;

class ListToDos {
	public final static String DEFAULT_FILE_NAME = "toDo_book.json";

	// Iterates though all tasks in the ToDoList and prints info about them.
	static void Print(ToDoList toDoList) {
		for (ToDo toDo : toDoList.getToDoList()) {
			System.out.println("Task: " + toDo.getTask());
			System.out.println("  Context: " + toDo.getContext());
			System.out.println("  Project: " + toDo.getProject());
			System.out.println("  Priority: " + toDo.getPriority());
			System.out.println();
		}
	}

	// Main function: Reads the entire task book from a file and prints all
	// the information inside.
	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();
		String filename = DEFAULT_FILE_NAME;
		if (args.length > 0) {
			filename = args[0];
		}

		// Read the existing task book.
		ToDoList toDoList = gson.fromJson(new FileReader(filename),
				ToDoList.class);

		Print(toDoList);
	}
}