package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TareaServlet
 */
@WebServlet("/AddToDo")
public class AddToDo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("task") == null || request.getParameter("task").isEmpty())
			response.sendRedirect("./");
		else{
			ToDo toDo = new ToDo();
			toDo.setTask(request.getParameter("task")!=null ? request.getParameter("task") : "");
			toDo.setContext(request.getParameter("context")!=null ? request.getParameter("context") : "");
			toDo.setProject(request.getParameter("project")!=null ? request.getParameter("project") : "");
			toDo.setPriority((request.getParameter("priority")==null || request.getParameter("priority").isEmpty()) ? -1 : Integer.parseInt(request.getParameter("priority")));
			
			try {
				ToDoList.addToDo(toDo);
			} catch (Exception e) {
				
			}
			response.sendRedirect("./ListToDos");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,  response);
	}

}