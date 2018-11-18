package hr.fer.zemris.java.hw07.crypto;

/**
 * Utility class used for handling conversions.
 * 
 * @author MarinoK
 */
public class Util {

	/** Numeric value of the hexadecimal base. */
	private static final int HEX_BASE = 16;

	/**
	 * Utility method used to convert string in a hexadecimal format to a array of
	 * bytes.
	 * 
	 * @param keyText
	 *            input string in a hexadecimal format
	 * @return byte array of decimal values converted from hexadecimal values
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0) throw new IllegalArgumentException("Length must be even.");
		char[] hexInput = keyText.toCharArray();
		byte[] byteOutput = new byte[keyText.length() / 2];
		for (int i = 0; i < hexInput.length; i += 2) {
			byteOutput[i / 2] = (byte) ((Character.digit(hexInput[i], HEX_BASE) * HEX_BASE)
					+ Character.digit(hexInput[i + 1], HEX_BASE));
		}
		return byteOutput;
	}

	/**
	 * Utility method used to convert array of bytes to a string with hexadecimal
	 * values.
	 * 
	 * @param bytearray
	 *            input digits in a byte array
	 * @return string with hexadecimal values
	 */
	public static String bytetohex(byte[] bytearray) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bytearray) {
			String x = Integer.toHexString((int) (b & 0xFF));
			if (x.length() == 1) {
				sb.append(String.valueOf(0));
			}
			sb.append(x);
		}
		if (sb.length() % 2 != 0) {
			sb.insert(0, 0);
		}
		return sb.toString();
	}
}
