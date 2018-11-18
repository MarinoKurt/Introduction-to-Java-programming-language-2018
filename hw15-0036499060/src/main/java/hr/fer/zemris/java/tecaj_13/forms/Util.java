package hr.fer.zemris.java.tecaj_13.forms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Utility class used for forms.
 * 
 * @author MarinoK
 */
public class Util {

	/** Encryption algorithm used for password storing. */
	private static final String ENCRYPTION_ALGORITHM = "SHA-1";

	/**
	 * Auxiliary method used to prepare string for web usage.
	 * 
	 * @param s
	 *            string to prepare
	 * @return empty string, if the given string was null, trimmed string otherwise
	 */
	public static String prepareString(String s) {
		if (s == null) return "";
		return s.trim();
	}

	/**
	 * Auxiliary method used to check are the login credentials valid.
	 * 
	 * @param nickname
	 *            that user provided
	 * @param password
	 *            that user provided
	 * @return true, if credentials are valid
	 */
	public static boolean checkLogin(String nickname, String password) {
		BlogUser be = DAOProvider.getDAO().getBlogUser(nickname);
		String attemptHash = calculateHash(password);
		if (attemptHash.equals(be.getPasswordHash())) {
			return true;
		}
		return false;
	}

	/**
	 * Auxiliary method used to check whether the provided nickname exists in the
	 * database.
	 * 
	 * @param nickname
	 *            to check
	 * @return true, if the nickname exists
	 */
	public static boolean userExists(String nickname) {
		BlogUser be = DAOProvider.getDAO().getBlogUser(nickname);
		return be != null;
	}

	/**
	 * Auxiliary method used to check whether the e-mail address is in the right
	 * form. Very primitive.
	 * 
	 * @param email
	 *            to check
	 * @return true, if the e-mail corresponds to the form
	 */
	public static boolean checkEmail(String email) {
		int l = email.length();
		int m = email.indexOf('@');
		int p = email.lastIndexOf('.');

		if (l < 5 || p == -1 || p == 0 || p == l - 1 || m > p || p == l - 1) {
			return false;
		}
		return true;
	}

	/**
	 * Auxiliary method used to calculate the hash of the given password.
	 * 
	 * @param password
	 *            to calculate the hash for
	 * @return SHA-1 hash of the password.
	 */
	public static String calculateHash(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(ENCRYPTION_ALGORITHM);
		} catch (NoSuchAlgorithmException ignorable) {
		}
		md.update(password.getBytes());
		String hash = bytetohex(md.digest());
		return hash;
	}

	/**
	 * Utility method used to convert array of bytes to a string with hexadecimal
	 * values.
	 * 
	 * @param bytearray
	 *            input digits in a byte array
	 * @return string with hexadecimal values
	 */
	private static String bytetohex(byte[] bytearray) {
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

	/**
	 * Auxiliary method used to check for password length.
	 * 
	 * @param password
	 *            to check
	 * @return false, if the password is shorter than 8 characters
	 */
	public static boolean checkPassword(String password) {
		if (password.length() < 8) return false;
		return true;
	}

}
