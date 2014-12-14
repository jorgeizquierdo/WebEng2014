var URI = "ws://localhost:8025/toDos";
var socket; 

/* Document Ready */
$(document).ready(function() { 
  
	$("#insert_btn").click(function() {
		if ( $('#task_insert').val().length == 0 || $('#context_insert').val().length == 0 || $('#project_insert').val().length == 0 || $('#priority_insert').val().length == 0 ) {
			alert("Please fill out all fields.");
		} else {
			toDo = JSON.stringify({    
				task: $('#task_insert').val(),
				context: $('#context_insert').val(),
				project: $('#project_insert').val(),
				priority: $('#priority_insert').val()
			});
			
			if($('#id_insert').val().length == 0){
				createToDo(toDo);
			}
			else{
				updateToDo($('#id_insert').val(), toDo);
			}
			
			$('#id_insert').val("");
			$('#task_insert').val("");
			$('#context_insert').val("");
			$('#project_insert').val("");
			$('#priority_insert').val("");
		}    
	});
	
	$("#search_btn").click(function() {
		var priority = $('#priority_search').val();
		if($('#priority_search').val().length == 0){
			priority = -1;
		}
		toDo = JSON.stringify({    
			task: $('#task_search').val(),
			context: $('#context_search').val(),
			project: $('#project_search').val(),
			priority: priority
		});
		
		getToDoListFilter(toDo);
	});
	
	connectServer();
  
});

function connectServer(){
	socket = new WebSocket(URI);
	socket.onopen = function(){
		getToDoList();
	}
	socket.onmessage = function(respuesta){
		respuesta = respuesta.data;
		var op = respuesta.substring(0, respuesta.indexOf("::"));
		switch (op) {
			case "addToDo":
			case "updateToDo":
			case "removeToDo":
				break;
			case "getToDoList":
			case "getToDoListFilter":
				var msg = JSON.parse(respuesta.substring(respuesta.indexOf("::") + 2));
				$('#list').find('tbody').empty();
				showToDoList(msg);
				break;
		}
	}
	socket.onerror = function(){
		alert("Error connecting to server");
	}
}

/* Shows tasks on table */
function showToDoList(toDoList) {  
	for (var i in toDoList){
		showToDo(toDoList[i]);
	}
	
	$(".delete_btn").click(function() {
		deleteTask($(this).closest('.task').data('id'));
	});
}

function showToDo(toDo) {  
	$('#list').find('tbody').append('<tr id="task_' + toDo.id + '" class="task" data-id="' + toDo.id + '">' +
										'<td style="width:6%">' + toDo.id + '</td>' +
										'<td style="width:20%">' + toDo.task + '</td>' +
										'<td style="width:20%">' + toDo.context + '</td>' +
										'<td style="width:20%">' + toDo.project + '</td>' +
										'<td style="width:6%">' + toDo.priority + '</td>' +
										'<td style="width:6%"><center><button class="delete_btn">Delete</button></center></td>' +
									'</tr>' );
}

/* SERVER CALLS */
function getToDoList() { 
	socket.send("getToDoList");
}

function getToDoListFilter(toDo) {  
	socket.send("getToDoListFilter::" + toDo);
}

function createToDo(toDo) {
	socket.send("addToDo::" + toDo);
}

function updateToDo(id, toDo) {
	socket.send("updateToDo::" + id + "::" + toDo);
}

function deleteTask(id) {
	socket.send("removeToDo::" + id);
}