package hr.fer.zemris.gallery;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Picture database provider. All communication with the database should go
 * through this provider.
 * 
 * @author MarinoK
 */
@WebListener
public class PictureDBProvider implements ServletContextListener {

	/** Instance of the database. */
	private static PictureDB db;

	/**
	 * Getter for the database instance.
	 * 
	 * @return database instance
	 */
	public static PictureDB getDB() {
		return db;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		db = new PictureDB(sce.getServletContext().getRealPath("/WEB-INF"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
