package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;

/**
 * Storage of connections towards the database.
 * 
 * @author marcupic
 */
public class SQLConnectionProvider {

	/**
	 * Map of connections towards the database.
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Sets the connection for the current thread, or deletes the connection from
	 * the map if the argument is null.
	 * 
	 * @param con
	 *            connection to the database, or null for deleting entry
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Fetch the connection that only the current thread can use.
	 * 
	 * @return connection
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}