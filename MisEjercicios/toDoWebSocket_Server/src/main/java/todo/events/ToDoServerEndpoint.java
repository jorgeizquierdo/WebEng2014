package todo.events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import todo.common.ToDo;
import todo.common.ToDoList;

@ServerEndpoint(value = "/toDos")
public class ToDoServerEndpoint {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	private static List<Session> sessionList = new ArrayList<Session>(); 

	@OnOpen
	public void onOpen(Session session) {
		sessionList.add(session);
		logger.info("Connected ... " + sessionList.size() + " :: " + session.getId());
	}

	@OnMessage
	public String onMessage(String message, Session session) {
		int sep = message.indexOf("::");
		String operation = sep > 0 ? message.substring(0, sep) : message;
		String msg = sep > 0 ? message.substring(sep + 2) : null;
		String response = null;
		boolean sendToAll = false;
		
		switch (operation) {
			case "addToDo":
				try {
					ToDoList.addToDo((new Gson()).fromJson(msg, ToDo.class));
					response = "Task sucessfully added";
					sendToAll = true;
				} catch (Exception e1) {
					response = "Task not sucessfully added";
				}
				break;
			case "updateToDo":
				try {
					sep = msg.indexOf("::");
					ToDoList.updateToDo(Integer.parseInt(msg.substring(0, sep)), (new Gson()).fromJson(msg.substring(sep + 2), ToDo.class));
					response = "Task sucessfully updated";
					sendToAll = true;
				} catch (Exception e1) {
					response = "Task not sucessfully updated";
				}
				break;
			case "removeToDo":
				try {
					ToDoList.removeToDo(Integer.parseInt(msg));
					response = "Task sucessfully removed";
					sendToAll = true;
				} catch (Exception e1) {
					response = "Task not sucessfully removed";
				}
				break;
			case "getToDoList":
				response = (new Gson()).toJson(ToDoList.getToDoList());
				break;
			case "getToDoListFilter":
				ToDo toDo = (new Gson()).fromJson(msg, ToDo.class);				
				response = (new Gson()).toJson(ToDoList.getToDoList(toDo.getTask(), toDo.getContext(), toDo.getProject(), toDo.getPriority()));
				break;
			case "quit":
				try {
					session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "End service"));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				break;
		}
		
		if(sendToAll){
			String toDos = (new Gson()).toJson(ToDoList.getToDoList());
			for(Iterator<Session> it = sessionList.iterator(); it.hasNext(); ){
				Session s = it.next();
				s.getAsyncRemote().sendText("getToDoList::" + toDos);
			}
		}
		return operation + "::" + response;
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		sessionList.remove(session);
		logger.info(String.format("Number of session: %d\nSession %s closed because of %s", sessionList.size(), session.getId(), closeReason));
	}
}
