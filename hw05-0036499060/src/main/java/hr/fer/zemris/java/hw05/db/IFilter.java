package hr.fer.zemris.java.hw05.db;

/**
 * Functional interface for filtering StudentRecords based on the implementation
 * of one method: accepts.
 * 
 * @author MarinoK
 */
public interface IFilter {
	
	/**
	 * Method used for filtering StudentRecords.
	 * 
	 * @param record
	 *            to be tested
	 * @return true, if the record satisfies the condition
	 */
	public boolean accepts(StudentRecord record);
}
