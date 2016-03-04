package org.dieschnittstelle.jee.esa.skeleton.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.dieschnittstelle.jee.esa.skeleton.jpa.Todo;

@Local
@Path("/todos")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public interface TodoCRUDLocal {

	@POST
	public Todo createTodo(Todo todo);

	@GET
	public List<Todo> readAllTodos();

	@GET
	@Path("/{id}")
	public Todo readTodo(@PathParam("id") long id);
	
}
