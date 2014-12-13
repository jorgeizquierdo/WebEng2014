package todo.rest;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import todo.common.ToDo;
import todo.common.ToDoList;

@Path("/toDos")
public class ToDoBookService {

	@Inject
	ToDoList toDoList;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ToDo> getToDoBook() {		
		return toDoList.getToDoList();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ToDo> getToDoBookFilter(@Context UriInfo info, ToDo toDo) {
		String task = toDo.getTask() != null ? toDo.getTask() : "";
		String context = toDo.getContext() != null ? toDo.getContext() : "";
		String project = toDo.getProject() != null ? toDo.getProject() : "";
		String priority = toDo.getPriority() < 0 ? "" : String.valueOf(toDo.getPriority());
		
		return toDoList.getToDoList(task, context, project, priority);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addToDo(@Context UriInfo info, ToDo toDo) {
		try {
			toDoList.addToDo(info, toDo);
			return Response.created(toDo.getHref()).entity(toDo).build();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getToDo(@PathParam("id") int id) {
		for (ToDo t : toDoList.getToDoList()) {
			if (t.getId() == id) {
				return Response.ok(t).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePerson(@Context UriInfo info,
			@PathParam("id") int id, ToDo toDo) {

		try {
			if(toDoList.updateToDo(id, toDo)){
				return Response.ok(toDo).build();
			}
			else{
				return Response.status(Status.BAD_REQUEST).build();
			}
		} catch (IOException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteToDo(@PathParam("id") int id) {
		try {
			if(toDoList.removeToDo(id)){
				return Response.noContent().build();
			}
			else{
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (IOException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
