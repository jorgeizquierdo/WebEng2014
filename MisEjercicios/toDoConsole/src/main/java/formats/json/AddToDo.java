package formats.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.google.gson.Gson;

public class AddToDo {

	public final static String DEFAULT_FILE_NAME = "toDo_book.json";

	// This function fills in a ToDo message based on user input.
	static ToDo PromptForToDo(BufferedReader stdin, PrintStream stdout)
			throws IOException {
		ToDo toDo = new ToDo();

		stdout.print("Enter task: ");
		toDo.setTask(stdin.readLine());

		stdout.print("Enter context: ");
		toDo.setContext(stdin.readLine());

		stdout.print("Enter project: ");
		toDo.setProject(stdin.readLine());

		stdout.print("Enter priority: ");
		toDo.setPriority(Integer.parseInt(stdin.readLine()));

		return toDo;
	}

	// Main function: Reads the entire task book from a file,
	// adds one task based on user input, then writes it back out to the same
	// file.
	public static void main(String[] args) throws Exception {
		String filename = DEFAULT_FILE_NAME;
		if (args.length > 0) {
			filename = args[0];
		}

		ToDoList toDoList = new ToDoList();
		Gson gson = new Gson();

		// Read the existing task book.
		try {
			toDoList = gson.fromJson(new FileReader(filename), ToDoList.class);
		} catch (FileNotFoundException e) {
			System.out.println(filename
					+ ": File not found.  Creating a new file.");
		}

		// Add a task.
		toDoList.addToDo(PromptForToDo(new BufferedReader(
				new InputStreamReader(System.in)), System.out));

		// Write the new task book back to disk.
		FileWriter output = new FileWriter(filename);
		output.write(gson.toJson(toDoList));
		output.close();
	}
}
