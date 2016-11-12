package com.github.poad.examples.database.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import com.github.poad.examples.database.entity.Message;

public class MessageResource {
	private final EntityManagerFactory emf;
	private final EntityManager em;

	public MessageResource() {
		emf = Persistence.createEntityManagerFactory("examples");
		em = emf.createEntityManager();
	}

	public long create(String message) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Message entity = new Message();
			try {
				entity.setMessage(message);
				em.persist(entity);
				em.flush();
				tx.commit();
				return entity.getId().longValue();
			} finally {
				em.detach(entity);
			}
		} catch (PersistenceException e) {
			tx.rollback();
			throw e;
		}
	}

	public String get(long id) {
		Message entity = em.createNamedQuery("Message.byId", Message.class).setParameter("id", Long.valueOf(id))
				.getSingleResult();
		try {
			return entity == null ? null : entity.getMessage();
		} finally {
			if (entity != null) {
				em.detach(entity);
			}
		}
	}

	public void update(long id, String message) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Message entity = em.find(Message.class, Long.valueOf(id));
			try {
				entity.setMessage(message);
				em.persist(entity);
				tx.commit();
			} finally {
				em.detach(entity);
			}
		} catch (PersistenceException e) {
			tx.rollback();
			throw e;
		}
	}

	public List<Message> list() {
		List<Message> list = em.createNamedQuery("Message.list", Message.class).getResultList();
		try {
			return list;
		} finally {
			list.forEach(i -> em.detach(i));
		}
	}

	public void delete(long id) {
		Message entity = em.find(Message.class, Long.valueOf(id));
		if (entity != null) {
			em.remove(entity);
		}
	}
}
