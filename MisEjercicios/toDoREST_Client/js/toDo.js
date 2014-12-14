/* Document Ready */
$(document).ready(function() { 
  
	getToDoList();
  
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
  
});

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
										toHTML(toDo) +
									'</tr>' );
}

function toHTML(toDo){
	return	'<td style="width:6%">' + toDo.id + '</td>' +
			'<td style="width:20%">' + toDo.task + '</td>' +
			'<td style="width:20%">' + toDo.context + '</td>' +
			'<td style="width:20%">' + toDo.project + '</td>' +
			'<td style="width:6%">' + toDo.priority + '</td>' +
			'<td style="width:6%"><center><button class="delete_btn">Delete</button></center></td>';
}

/* SERVER CALLS */
var REST_URI = "http://localhost:8080/toDos";

function getToDoList() {  
	$.ajax({
		url : REST_URI,
		contentType : 'application/json',
		type : 'GET',
		success : function (data)
		{
			$('#list').find('tbody').empty();
			showToDoList(data);
		}
	});
}

function getToDoListFilter(toDo) {  
	$.ajax({
		type : 'PUT',
		contentType : 'application/json',
		url : REST_URI,
		dataType : "json",
		data : toDo,
		success : function (data)
		{
			$('#list').find('tbody').empty();
			showToDoList(data);
		}
	});
}

function createToDo(toDo) {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : REST_URI,
		dataType : "json",
		data : toDo,		
		success : function(todo) {
			showToDo(todo);
									
			$(".delete_btn").click(function() {
				deleteTask($(this).closest('.task').data('id'));
			});
		}
	});
}

function updateToDo(id, toDo) {
	$.ajax({
		type : 'PUT',
		contentType : 'application/json',
		url : REST_URI + "/" + id,
		dataType : "json",
		data : toDo,		
		success : function(todo) {      
			$('#task_' + id).html(toHTML(todo));
									
			$(".delete_btn").click(function() {
				deleteTask($(this).closest('.task').data('id'));
			});
		}
	});
}

function deleteTask(id) {
	$.ajax({
		url: REST_URI + "/" + id,
		contentType : 'application/json',
		type:  'DELETE',
		success : function() {
			$('#task_' + id).remove();
		}
	});
}