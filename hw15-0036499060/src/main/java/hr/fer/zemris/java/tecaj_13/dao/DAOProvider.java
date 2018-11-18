package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Singleton class which knows what to return as the provider for the data.
 * 
 * @author MarinoK
 */
public class DAOProvider {

	/**
	 * Data access object.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Fetches the data access object.
	 * 
	 * @return data access object
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}