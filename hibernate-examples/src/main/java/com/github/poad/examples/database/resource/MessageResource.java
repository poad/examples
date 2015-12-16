package com.github.poad.examples.database.resource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import com.github.poad.examples.database.entity.Message;

public class MessageResource {

	public long create(String message) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("examples");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Message entity = new Message();
			entity.setMessage(message);
			em.persist(entity);
			em.flush();
			tx.commit();
			return entity.getId().longValue();
		} catch (PersistenceException e) {
			tx.rollback();
			throw e;
		}
	}
}
