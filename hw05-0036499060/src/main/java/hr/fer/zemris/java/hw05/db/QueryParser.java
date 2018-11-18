package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

/**
 * Parser for the database queries.
 * 
 * @author MarinoK
 */
public class QueryParser {

	/** Reference to lexer communicating with this parser. */
	private QueryLexer lexer;

	/**
	 * Logical value is the current query direct. A query is direct if it requires
	 * only jmbag value, and gives the exact value of that jmbag (uses = operator).
	 */
	private boolean isDirectQuery;

	/**
	 * Stores the queried JMBAG, in direct queries.
	 */
	private String queriedJMBAG;

	/**
	 * List of conditional expressions that our output must satisfy for the given
	 * query.
	 */
	private List<ConditionalExpression> query;

	/** Last given token from lexer. */
	private QueryToken lexerOutput;

	/**
	 * Constructor for the QueryParser.
	 * 
	 * @param text
	 *            to parse
	 */
	public QueryParser(String text) {

		isDirectQuery = false;
		query = new LinkedList<>();
		lexer = new QueryLexer(text);
		parse();
	}

	/**
	 * Logical value is the current query direct. A query is direct if it requires
	 * only jmbag value, and gives the exact value of that jmbag (uses = operator).
	 *
	 * @return true, if the query is direct
	 */
	boolean isDirectQuery() {
		return this.isDirectQuery;
	}

	/**
	 * Gives the queried JMBAG only if the query is direct. A query is direct if it
	 * requires only jmbag value, and gives the exact value of that jmbag (uses =
	 * operator).
	 * 
	 * @return quariedJMBAG, if the query is direct
	 * 
	 * @throws UnsupportedOperationException
	 *             if the query is not direct
	 */
	String getQueriedJMBAG() { // fast retrieval
		if (isDirectQuery) {
			return this.queriedJMBAG;
		} else {
			throw new UnsupportedOperationException("Queried JMBAG is avaliable only from direct queries.");
		}
	}

	/**
	 * Getter for the query.
	 * 
	 * @return query as a list of conditional expressions
	 */
	List<ConditionalExpression> getQuery() {
		return this.query;
	}

	/**
	 * Method used to communicate with the lexer, and parse the given text.
	 */
	private void parse() {
		lexerOutput = lexer.nextToken();
		while (lexerOutput.getType() != QueryTokenType.EOQ) {

			IFieldValueGetter fieldGetter = null;
			String literal = null;
			IComparisonOperator comparisonOperator = null;

			if (lexerOutput.getType().equals(QueryTokenType.ATTRIBUTE_NAME)) {
				String attributeName = lexerOutput.getValue();
				fieldGetter = recognizeFieldGetter(attributeName);

				lexerOutput = lexer.nextToken();
				if (lexerOutput.getType().equals(QueryTokenType.OPERATOR)) {
					String operator = lexerOutput.getValue();
					comparisonOperator = recognizeOperator(operator);

					lexerOutput = lexer.nextToken();
					if (lexerOutput.getType().equals(QueryTokenType.STRING_LITERAL)) {
						literal = lexerOutput.getValue();
					}
				}

			}

			if (fieldGetter == null || literal == null || comparisonOperator == null) {
				throw new QueryParserException("Query does not contain legal subqueries.");
			}

			ConditionalExpression x = new ConditionalExpression(fieldGetter, literal, comparisonOperator);
			query.add(x);

			lexerOutput = lexer.nextToken();
			if (!(lexerOutput.getType().equals(QueryTokenType.LOGICAL_OPERATOR_AND)
					|| lexerOutput.getType().equals(QueryTokenType.EOQ))) {
				throw new QueryParserException("Query does not contain legal subqueries.");
			} else {

				if (lexerOutput.getType().equals(QueryTokenType.LOGICAL_OPERATOR_AND)) {
					lexerOutput = lexer.nextToken();
					if (!lexerOutput.getType().equals(QueryTokenType.ATTRIBUTE_NAME)) {
						throw new QueryParserException("Query does not contain legal subqueries.");
					}
				}
			}
		}
		if (query.size() == 1 && query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS)
				&& query.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG)) {
			this.queriedJMBAG = query.get(0).getStringLiteral();
			isDirectQuery = true;

		}

	}

	/**
	 * Auxiliary method to recognize field value from given text.
	 * 
	 * @param attributeName
	 *            string given from lexer
	 * @return corresponding instance of IFieldValueGetter
	 */
	private IFieldValueGetter recognizeFieldGetter(String attributeName) {
		switch (attributeName) {
		case "firstName":
			return FieldValueGetters.FIRST_NAME;
		case "lastName":
			return FieldValueGetters.LAST_NAME;
		case "jmbag":
			return FieldValueGetters.JMBAG;
		default:
			throw new QueryParserException("Attribute unrecognizable.");
		}

	}

	/**
	 * Auxiliary method to recognize operator from given text.
	 * 
	 * @param operator
	 *            string given from lexer
	 * @return corresponding instance of IComparisonOperator
	 */
	private IComparisonOperator recognizeOperator(String operator) {
		switch (operator) {
		case "=":
			return ComparisonOperators.EQUALS;
		case "!=":
			return ComparisonOperators.NOT_EQUAL;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case ">":
			return ComparisonOperators.GREATER;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case "<":
			return ComparisonOperators.LESS;
		case "LIKE":
			return ComparisonOperators.LIKE;
		default:
			throw new QueryParserException("Operator unrecognizable.");
		}
	}

}
