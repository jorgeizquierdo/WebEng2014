# JSON with GSON
This project contains a demonstration of [GSON](https://code.google.com/p/google-gson/). Gson is a Java library that can be used to convert arbitrary Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object.

The following examples are included:
* __AddToDo__ (```gradle -q addToDo```) tests first it the file ```toDo_book.json``` exists. If it exists, the example unmarshalls its content into Java objects. Example code expects that the file contains an ```ToDoList``` instance. Then, code asks to the user the details of a ```ToDo``` and then the toDo is added to the book. Finally, the book is marshalled into a JSON file with name ```toDo_book.json```.
* __ListToDos__ (```gradle -q listToDos```) unmarshalls ```toDo_book.json``` into Java objects and then dump the objects to the console.
