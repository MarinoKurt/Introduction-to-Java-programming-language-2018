package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Provider of the entity manager factory.
 * 
 * @author MarinoK
 */
public class JPAEMFProvider {

	/** Entity manager factory. */
	public static EntityManagerFactory emf;

	/**
	 * Getter for the entity manager factory.
	 * 
	 * @return entity manager factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Setter for the entity manager factory.
	 * 
	 * @param emf
	 *            to set
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}