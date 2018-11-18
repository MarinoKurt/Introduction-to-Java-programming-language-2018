package hr.fer.zemris.java.hw05.db;

/**
 * Functional interface used to get StudentRecord's attributes.
 * 
 * @author MarinoK
 */
public interface IFieldValueGetter {

	/**
	 * Method takes a StudentRecord and returns one attribute of the record.
	 * 
	 * @param record
	 *            whose attribute is needed
	 * @return implemented attribute
	 */
	public String get(StudentRecord record);
}
