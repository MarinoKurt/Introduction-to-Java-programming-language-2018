package hr.fer.zemris.java.hw05.db;

/**
 * Types of QueryToken used in database queries.
 * 
 * @author MarinoK
 */
public enum QueryTokenType {

	/** Name of the StudentRecord attribute. */
	ATTRIBUTE_NAME,

	/** Value of the StudentRecord attribute. */
	STRING_LITERAL,

	/** Operator (>, <, >=, <=, =, !=, LIKE). */
	OPERATOR,

	/** Logical operator and. */
	LOGICAL_OPERATOR_AND,
	
	/** Representing the end of query. */
	EOQ
}
