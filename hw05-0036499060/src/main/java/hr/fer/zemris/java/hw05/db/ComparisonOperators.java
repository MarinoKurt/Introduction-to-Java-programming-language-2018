package hr.fer.zemris.java.hw05.db;

/**
 * Various implementations for the IComparisonOperator interface used for
 * comparing strings.
 * 
 * @author MarinoK
 */
public class ComparisonOperators {

	/**
	 * Strings satisfy this operator if the first one is lexically smaller than the
	 * second one.
	 */
	public static final IComparisonOperator LESS = (v1, v2) -> {
		if (v1.compareTo(v2) < 0) return true;
		return false;
	};

	/**
	 * Strings satisfy this operator if the first one is lexically smaller or equal
	 * to the second one.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> {
		if (v1.compareTo(v2) <= 0) return true;
		return false;
	};

	/**
	 * Strings satisfy this operator if the first one is lexically greater than the
	 * second one.
	 */
	public static final IComparisonOperator GREATER = (v1, v2) -> {
		if (v1.compareTo(v2) > 0) return true;
		return false;
	};

	/**
	 * Strings satisfy this operator if the first one is lexically greater or equal
	 * to the second one.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> {
		if (v1.compareTo(v2) >= 0) return true;
		return false;
	};

	/**
	 * Strings satisfy this operator if the first one is lexically equal to the
	 * second one.
	 */
	public static final IComparisonOperator EQUALS = (v1, v2) -> {
		if (v1.compareTo(v2) == 0) return true;
		return false;
	};

	/**
	 * Strings satisfy this operator if the first one is lexically unequal to the
	 * second one.
	 */
	public static final IComparisonOperator NOT_EQUAL = (v1, v2) -> {
		if (v1.compareTo(v2) != 0) return true;
		return false;
	};

	/**
	 * Strings satisfy this operator if the first string satisfies the pattern
	 * contained in the second string. Character * replaces one or more characters
	 * of any value. It is illegal to give multiple * characters in one string.
	 */
	public static final IComparisonOperator LIKE = (v1, v2) -> {
		int first = v2.indexOf("*");
		int last = v2.lastIndexOf("*");
		if (first != last || !v2.contains("*")) {
			throw new IllegalArgumentException("Must use one wildcard for this operator.");
		}
		if (v2.startsWith("*")) {
			if (v1.endsWith(v2.substring(1, v2.length()))) return true;
		} else if (v2.endsWith("*")) {
			if (v1.startsWith(v2.substring(0, v2.length() - 1))) return true;
		} else {
			String[] parts = v2.split("\\*");
			if (v1.length() >= v2.length() && v1.startsWith(parts[0]) && v1.endsWith(parts[1])) {
				return true;
			}
		}
		return false;
	};
}
