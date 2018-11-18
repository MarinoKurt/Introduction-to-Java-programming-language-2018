package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Implementation of the data access object which uses Java Persistance API to
 * communicate with the database.
 * 
 * @author MarinoK
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getBlogUser(String nickname) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		List<BlogUser> blogUsers = em.createQuery("SELECT t FROM BlogUser t where t.nick=:nickname", BlogUser.class)
				.setParameter("nickname", nickname).getResultList();
		if (blogUsers.isEmpty()) {
			return null;
		}
		return blogUsers.get(0);
	}

	@Override
	public void storeUser(BlogUser user) {
		JPAEMProvider.getEntityManager().persist(user);
	}

	@Override
	public List<BlogUser> getUsers() {
		EntityManager em = JPAEMProvider.getEntityManager();
		List<BlogUser> blogUsers = em.createQuery("SELECT t FROM BlogUser t", BlogUser.class).getResultList();
		return blogUsers;
	}

	@Override
	public BlogEntry storeEntry(BlogEntry createdEntry) {
		JPAEMProvider.getEntityManager().persist(createdEntry);
		return createdEntry;

	}

	@Override
	public BlogComment storeComment(BlogComment comment) {
		JPAEMProvider.getEntityManager().persist(comment);
		return comment;
	}

}