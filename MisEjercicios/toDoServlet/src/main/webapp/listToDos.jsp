<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//ES" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.List" %>
<%@ page import="servlets.ToDo" %>
<%@ page import="servlets.ToDoList" %>
<%
	String task = "", context = "", project = "", priority = "";

	if(request != null){
		task = request.getParameter("task")!=null ? request.getParameter("task") : "";
		context = request.getParameter("context")!=null ? request.getParameter("context") : "";	
		project = request.getParameter("project")!=null ? request.getParameter("project") : "";	
		priority = request.getParameter("priority")!=null ? request.getParameter("priority") : "";	
	}
	
	List<ToDo> toDoList = ToDoList.getToDoList(task, context, project, priority);
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
			<center><h1>Lista de tareas</h1></center>
			<div>
				<table style="width:100%; border-collapse: collapse" border="1">
					<tr>
						<th style="width:25%"><center>Task</center></td>
						<th style="width:25%"><center>Context</center></td>
						<th style="width:25%"><center>Project</center></td>
						<th style="width:25%"><center>Priority</center></td>
					</tr>
				<%		
					for(ToDo toDo: toDoList){
				%>
					<tr>
						<td style="width:25%"><%=toDo.getTask()%></td>
						<td style="width:25%"><%=toDo.getContext()%></td>
						<td style="width:25%"><%=toDo.getProject()%></td>
						<td style="width:25%"><%=toDo.getPriority()%></td>
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

