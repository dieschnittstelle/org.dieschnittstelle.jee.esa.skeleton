package org.dieschnittstelle.jee.esa.skeleton.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dieschnittstelle.jee.esa.skeleton.jpa.Todo;

@Stateless
public class TodoCRUDStateless implements TodoCRUDLocal {
	
	@PersistenceContext(unitName="skeleton_PU")
	private EntityManager em;

	@Override
	public Todo createTodo(Todo todo) {
		em.persist(todo);
		return todo;
	}

	@Override
	public List<Todo> readAllTodos() {
		return em.createQuery("FROM Todo").getResultList();
	}

	@Override
	public Todo readTodo(long id) {
		return em.find(Todo.class, id);
	}

}
