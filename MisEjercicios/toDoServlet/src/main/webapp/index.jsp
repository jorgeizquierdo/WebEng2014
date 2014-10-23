<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//ES" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%
	String task = "", context = "", project = "", priority = "";

	if(request != null){
		task = request.getParameter("task")!=null ? request.getParameter("task") : "";
		context = request.getParameter("context")!=null ? request.getParameter("context") : "";	
		project = request.getParameter("project")!=null ? request.getParameter("project") : "";	
		priority = request.getParameter("priority")!=null ? request.getParameter("priority") : "";	
	}
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
		<div>
			<table style="width:100%" border="1">
				<tr>
					<td>
						<center>
							<form action="./AddToDo" method="post">
								<table>
									<tr>
										<td colspan=2>
											<center>
												<h1>New task</h1>
											</center>
										</td>
									</tr>
									
									<tr>
										<td>Task:<i>*</i></td>
										<td width="40px"><input type="text" name="task" size="50" tabindex="1"/></td>							
									</tr>
									<tr>
										<td>Context:<i>*</i></td>
										<td width="40px"><input type="text" name="context" size="50" tabindex="2"/></td>
									</tr>
									<tr>
										<td>Project:<i>*</i></td>
										<td width="40px"><input type="text" name="project" size="50" tabindex="3"/></td>
									</tr>
									<tr>
										<td>Priority:<i>*</i></td>
										<td width="40px"><input type="number" name="priority" size="50" tabindex="4"/></td>
									</tr>
									<tr>
										<td colspan=2>
											<center>
												<input type="submit" value="Acept"/>
											</center>
										</td>
									</tr>
								</table>
							</form>
						</center>
					</td>
					<td>
						<center>
							<form action="./ListToDos" method="post">
								<table>
									<tr>
										<td colspan=2>
											<center>
												<h1>Search task</h1>
											</center>
										</td>
									</tr>
									
									<tr>
										<td>Task:</td>
										<td width="40px"><input type="text" name="task" size="50" tabindex="5"/></td>							
									</tr>
									<tr>
										<td>Context:</td>
										<td width="40px"><input type="text" name="context" size="50" tabindex="6"/></td>
									</tr>
									<tr>
										<td>Project:</td>
										<td width="40px"><input type="text" name="project" size="50" tabindex="7"/></td>
									</tr>
									<tr>
										<td>Priority:</td>
										<td width="40px"><input type="number" name="priority" size="50" tabindex="8"/></td>
									</tr>
									<tr>
										<td colspan=2>
											<i>* Empty for all task</i>
										</td>
									</tr>
									<tr>
										<td colspan=2>
											<center>
												<input type="submit" value="Search"/>
											</center>
										</td>
									</tr>
								</table>
							</form>
						</center>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>

