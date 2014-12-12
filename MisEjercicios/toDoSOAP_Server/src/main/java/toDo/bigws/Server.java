package toDo.bigws;
import javax.xml.ws.Endpoint;

public class Server {
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8081/toDo", new ToDoWebService());
	}

}
