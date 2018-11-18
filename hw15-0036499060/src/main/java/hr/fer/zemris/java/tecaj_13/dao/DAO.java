package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Contract used for communication between the persistence layer and application
 * logic.
 * 
 * @author MarinoK
 *
 */
public interface DAO {

	/**
	 * Fetches the blog entry with the given ID, or null if it doesn't exist in the
	 * database.
	 * 
	 * @param id
	 *            to search by
	 * @return blog entry with the given ID
	 * @throws DAOException
	 *             if an error occurs
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Fetches the blog user with the given nickname, or null if it doesn't exist in
	 * the database.
	 * 
	 * @param nickname
	 *            to search by
	 * @return blog user with the given nickname
	 * @throws DAOException
	 *             if an error occurs
	 */
	public BlogUser getBlogUser(String nickname) throws DAOException;

	/**
	 * Stores the given user to the database.
	 * 
	 * @param user
	 *            to store
	 */
	public void storeUser(BlogUser user);

	/**
	 * Stores the given user to the database.
	 * 
	 * @param entry
	 *            to store
	 * @return the stored entry
	 */
	public BlogEntry storeEntry(BlogEntry entry);

	/**
	 * Stores the given comment to the database.
	 * 
	 * @param comment
	 *            to store
	 * @return the stored comment
	 */
	public BlogComment storeComment(BlogComment comment);

	/**
	 * Returns all users in form of a list.
	 * 
	 * @return a list of all the users
	 */
	public List<BlogUser> getUsers();

}