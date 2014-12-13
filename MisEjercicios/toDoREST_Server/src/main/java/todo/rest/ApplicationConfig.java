package todo.rest;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import todo.common.ToDoList;

public class ApplicationConfig extends ResourceConfig {

	/**
     * Default constructor
     */
    public ApplicationConfig() {
    	this(new ToDoList());
    }


    public ApplicationConfig(final ToDoList toDoList) {
    	register(CrossDomainFilter.class);
    	register(ToDoBookService.class);
    	register(MOXyJsonProvider.class);
    	register(new AbstractBinder() {

			@Override
			protected void configure() {
				bind(toDoList).to(ToDoList.class);
			}});
	}	

}
