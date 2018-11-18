package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

/**
 * Singleton class which knows what to return as the provider for the data.
 * 
 * @author marcupic
 */
public class DAOProvider {

	/**
	 * Data access object.
	 */
	private static DAO dao = new SQLDAO();

	/**
	 * Fetches the data access object.
	 * 
	 * @return data access object
	 */
	public static DAO getDao() {
		return dao;
	}

}