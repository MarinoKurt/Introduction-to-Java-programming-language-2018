package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Container for the value of any type. However, if the user wants to use the
 * arithmetic operations on the value, value must be of type Integer, Double or
 * String. Otherwise, exception will occur while calculating with value.
 * 
 * @author MarinoK
 */
public class ValueWrapper {

	/**
	 * Value contained in this ValueWrapper. Can be of any type.
	 */
	private Object value;

	/**
	 * Boolean used to determine what data type is used for the result.
	 */
	private boolean resultIsDecimal;

	/**
	 * Constructor for the ValueWrapper.
	 * 
	 * @param value
	 *            to be contained in the wrapper
	 */
	public ValueWrapper(Object value) {
		this.value = value;
		this.resultIsDecimal = false;
	}

	/**
	 * Setter for the value.
	 * 
	 * @param value
	 *            current value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Getter for the value.
	 * 
	 * @return current value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Method used for adding the given value to the current value.
	 * 
	 * @param incValue
	 *            value to be added to the current value
	 */
	public void add(Object incValue) {
		Number currentValue = normalize(this.value);
		Number argument = normalize(incValue);

		if (resultIsDecimal) {
			value = currentValue.doubleValue() + argument.doubleValue();
			resultIsDecimal = false;
		} else {
			value = currentValue.intValue() + argument.intValue();
		}
	}

	/**
	 * Method used for subtract the given value from the current value.
	 * 
	 * @param decValue
	 *            value to be subtracted from the current value
	 */
	public void subtract(Object decValue) {
		Number currentValue = normalize(this.value);
		Number argument = normalize(decValue);

		if (resultIsDecimal) {
			value = currentValue.doubleValue() - argument.doubleValue();
			resultIsDecimal = false;
		} else {
			value = currentValue.intValue() - argument.intValue();
		}
	}

	/**
	 * Method used for multiplying the given value with the current value.
	 * 
	 * @param mulValue
	 *            value to multiply the current value with
	 */
	public void multiply(Object mulValue) {
		Number currentValue = normalize(this.value);
		Number argument = normalize(mulValue);

		if (resultIsDecimal) {
			value = currentValue.doubleValue() * argument.doubleValue();
			resultIsDecimal = false;
		} else {
			value = currentValue.intValue() * argument.intValue();
		}
	}

	/**
	 * Method used dividing the current value with the given value.
	 * 
	 * @param divValue
	 *            value to divide the current value with
	 * @throws NumberFormatException
	 *             when dividing by zero (or null)
	 */
	public void divide(Object divValue) {
		if (divValue == null) throw new NumberFormatException("Division with zero not defined.");

		Number currentValue = normalize(this.value);
		Number argument = normalize(divValue);

		if (resultIsDecimal) {
			value = currentValue.doubleValue() / argument.doubleValue();
			resultIsDecimal = false;
		} else {
			value = currentValue.intValue() / argument.intValue();
		}
	}

	/**
	 * Comparator for the numeric values.
	 * 
	 * @param withValue
	 *            current value will be compared to this value
	 * @return 0, if the values are equal, -1 if the given argument is bigger than
	 *         the current value, 1 otherwise
	 */
	public int numCompare(Object withValue) {
		Number currentValue = normalize(this.value);
		Number argument = normalize(withValue);
		if (!resultIsDecimal) {
			if (currentValue.intValue() == argument.intValue()) return 0;
			if (currentValue.intValue() > argument.intValue()) return -1;
			return 1;
		} else {
			resultIsDecimal = false;
			if (currentValue.doubleValue() == argument.doubleValue()) return 0;
			if (currentValue.doubleValue() > argument.doubleValue()) return -1;
			return 1;
		}
	}

	/**
	 * Auxiliary method used to normalize the number, prepare it for the arithmetic
	 * operations. Will treat null references as an integer value of zero.
	 * 
	 * @param unknownType
	 *            object given to arithmetic operator
	 * @return the number ready for arithmetic operations, as an instance of Number
	 * @throws RuntimeException
	 *             if the data type of of the given value is not applicable for
	 *             arithmetic operations
	 * 
	 */
	private Number normalize(Object unknownType) {
		if (unknownType == null) return 0;

		if (unknownType.getClass() == Integer.class) {
			return (Number) unknownType;
		}
		if (unknownType.getClass() == Double.class) {
			resultIsDecimal = true;
			return (Number) unknownType;
		}
		if (unknownType.getClass() == String.class) {
			String argument = String.valueOf(unknownType);
			if (argument.contains(".") || argument.toLowerCase()
					.contains("e")) {
				resultIsDecimal = true;
			}
			return Double.valueOf(argument);
		}
		System.out.println(unknownType.getClass());
		throw new RuntimeException("Unsupported type for the wrapper value: " + unknownType);
	}
}
