package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * Subject in the Listener pattern.
 * 
 * @author MarinoK
 */
public interface ModificationSubject {

	/**
	 * Adds the given modification listener.
	 * 
	 * @param l
	 *            listener to add
	 */
	void addModificationListener(ModificationListener l);

	/**
	 * Removes the given modification listener.
	 * 
	 * @param l
	 *            listener to remove
	 */
	void removeModificationListener(ModificationListener l);
}
