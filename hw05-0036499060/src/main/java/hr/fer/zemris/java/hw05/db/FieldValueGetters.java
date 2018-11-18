package hr.fer.zemris.java.hw05.db;

/**
 * Various implementations of IFieldValueGetter interface used for fetching the
 * StudentRecord's attributes.
 * 
 * @author MarinoK
 */
public class FieldValueGetters {

	/**
	 * Used for fetching StudentRecord's first name.
	 */
	public static final IFieldValueGetter FIRST_NAME = (record) -> record.getFirstName();

	/**
	 * Used for fetching StudentRecord's first name.
	 */
	public static final IFieldValueGetter LAST_NAME = (record) -> record.getLastName();

	/**
	 * Used for fetching StudentRecord's first name.
	 */
	public static final IFieldValueGetter JMBAG = (record) -> record.getJmbag();
}
