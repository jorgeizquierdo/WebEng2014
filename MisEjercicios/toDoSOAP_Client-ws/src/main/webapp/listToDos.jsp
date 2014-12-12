<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//ES" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.List" %>
<%@ page import="todo.bigws.ToDo" %>
<%@ page import="todo.bigws.ToDoWebServiceService" %>
<%
	String task = "", context = "", project = "";
	int priority = -1;

	if(request != null){
		task = request.getParameter("task")!=null ? request.getParameter("task") : "";
		context = request.getParameter("context")!=null ? request.getParameter("context") : "";	
		project = request.getParameter("project")!=null ? request.getParameter("project") : "";	
		priority = (request.getParameter("priority")==null || request.getParameter("priority").isEmpty()) ? -1 : Integer.parseInt(request.getParameter("priority"));
	}
	
	List<ToDo> toDoList = ((new ToDoWebServiceService()).getToDoWebServicePort()).listToDoFilter(task, context, project, priority);
%>
<html>
	<head>
		<style>
			th, td {
				padding: 5px;
				text-align: left;
			}
		</style>
	</head>
	<body>
			<center><h1>Task list</h1></center>
			<div>
				<table style="width:100%; border-collapse: collapse" border="1">
					<tr>
						<th style="width:23%"><center>Task</center></td>
						<th style="width:23%"><center>Context</center></td>
						<th style="width:23%"><center>Project</center></td>
						<th style="width:23%"><center>Priority</center></td>
						<th style="width:8%"></td>
					</tr>
				<%		
					for(ToDo toDo: toDoList){
				%>
					<tr>
						<td style="width:23%"><%=toDo.getTask()%></td>
						<td style="width:23%"><%=toDo.getContext()%></td>
						<td style="width:23%"><%=toDo.getProject()%></td>
						<td style="width:23%"><%=toDo.getPriority()%></td>
						<td style="width:8%"><center><a href="./DeleteToDo?task=<%=toDo.getTask()%>"><button>Delete</button></a></center></td>
					</tr>
				<%
					}
				%>
				</table>
				<br/>
				<center>
					<a href="./"><button>Return</button></a>
				</center>
			</div>
	</body>
</html>

