package org.dieschnittstelle.jee.esa.skeleton.webapp.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.dieschnittstelle.jee.esa.skeleton.ejb.TodoCRUDLocal;
import org.dieschnittstelle.jee.esa.skeleton.jpa.Todo;

@ManagedBean(name="vc")
@ApplicationScoped
public class TodoViewController {
	
	
	@EJB
	private TodoCRUDLocal todoCRUD;

	/*
	 * this attribute holds data for a new todo instance that might be created via the UI
	 */
	private Todo newTodo = new Todo();
	
	/*
	 * this attribute holds the list of all todos
	 */
	private List<Todo> allTodos = new ArrayList<Todo>();
	
	public List<Todo> getAllTodos() {
		if (this.allTodos.size() == 0) {
			this.allTodos = todoCRUD.readAllTodos();
		}

		return this.allTodos;
	}
	
	public Todo getNewTodo() {
		return this.newTodo;
	}
	
	public String createNewTodo() {
		// add the todo to the database
		Todo created = this.todoCRUD.createTodo(this.newTodo);
		// update the local list of all todos
		this.allTodos.add(created);
		// reset the newTodo object
		this.newTodo = new Todo();

		return "";
	}
	
	
	/*
	 * this method initially creates two todos 
	 */
	@PostConstruct
	public void createInitialTodos() {
		this.todoCRUD.createTodo(new Todo("lorem","ipsum dolor"));
		this.todoCRUD.createTodo(new Todo("dorem","lipsum olor"));
	}
	
}
