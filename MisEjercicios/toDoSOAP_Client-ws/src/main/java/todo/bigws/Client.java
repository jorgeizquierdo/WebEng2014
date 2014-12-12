package todo.bigws;


public class Client {
 	
	public static void main(String[] args) {
		ToDoWebServiceService hwss = new ToDoWebServiceService();
		ToDoWebService hws = hwss.getToDoWebServicePort();

		System.out.println(hws.addToDo("Tarea de prueba", "contexto", "", 1));
		
	}

}
