package org.dieschnittstelle.jee.esa.skeleton.webapp.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.skeleton.ejb.TodoCRUDLocal;
import org.dieschnittstelle.jee.esa.skeleton.jpa.Todo;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

@ManagedBean(name="vc")
@ApplicationScoped
public class TodoViewController {

	protected static Logger logger = Logger.getLogger(TodoViewController.class);

	// this annotation injects an ejb proxy
	@EJB
	private TodoCRUDLocal todoCRUD;

	// set this to true in case a REST service proxy shall be used, which will replace the ejb proxy
	private boolean useRESTServiceProxy = false;

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
	
	
	@PostConstruct
	public void initialise() {
		if (useRESTServiceProxy) {
			// 1. create a client
			ResteasyClient client = (new ResteasyClientBuilder()).build();

			// 2. create a ResteasyWebTarget from client
			ResteasyWebTarget target = client.target("http://localhost:8080/org.dieschnittstelle.jee.esa.skeleton.webapp/api");

			// 3. instantiate serviceClient using proxy() on target and passing interface
			todoCRUD = target.proxy(TodoCRUDLocal.class);
		}
		logger.info("initialise(): using crud proxy: " + todoCRUD);

		// create some dummy data
		createInitialTodos();
	}


	/*
	 * this method initially creates two todos
	 */
	public void createInitialTodos() {
		this.todoCRUD.createTodo(new Todo("lorem","ipsum dolor"));
		this.todoCRUD.createTodo(new Todo("dorem","lipsum olor"));
	}
	
}
