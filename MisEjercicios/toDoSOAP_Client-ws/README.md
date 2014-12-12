# To Do Client
This project contains a demonstration of a web client build with [JAX-WS](http://docs.oracle.com/javaee/6/tutorial/doc/bnayl.html). 

This project requires that running ```toDoSOAP_Server``` server. 

One of the first steps is the generation of the code required to invoke the client from
[ToDoSOAP_ClientService.wsdl](src/main/wsdl/ToDoSOAP_ClientService.wsdl) file. Look at [build.gradle](build.gradle) to get a glimpse of the 
complexities of the code generation process. If you want to create only the code just run ```gradle wsimport```.

The client can be run with the command ```gradle client``` (This example only insert a new toDo example). 

The web app client can be run with the command ```gradle jettyEclipseRun```. This command run the web app in [http://localhost:8080](http://localhost:8080)
