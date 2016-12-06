package org.dieschnittstelle.jee.esa.skeleton.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.skeleton.jpa.Todo;

@Stateless
public class TodoCRUDStateless implements TodoCRUDLocal {

	protected static Logger logger = Logger.getLogger(TodoCRUDStateless.class);
	
	@PersistenceContext(unitName="skeleton_PU")
	private EntityManager em;

	@Override
	public Todo createTodo(Todo todo) {
		logger.info("createTodo(): " + todo);
		em.persist(todo);
		return todo;
	}

	@Override
	public List<Todo> readAllTodos() {
		logger.info("readAllTodos()");
		return em.createQuery("FROM Todo").getResultList();
	}

	@Override
	public Todo readTodo(long id) {
		logger.info("readTodo(): " + id);
		return em.find(Todo.class, id);
	}

}
