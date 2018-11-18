package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Form used to check whether the login is complete, and suitable for
 * authentication.
 * 
 * @author MarinoK
 */
public class LoginForm {

	/** User nickname. */
	private String nick;

	/** User password. */
	private String password;

	/** Hash of the user password. */
	private String passwordHash;

	/** Map of missteps user made while filling the form. */
	Map<String, String> missteps = new HashMap<>();

	/** Default constructor of the form. */
	public LoginForm() {
	}

	/**
	 * Fetches the misstep for the given property of the login.
	 * 
	 * @param property
	 *            whose misstep it fetches
	 * @return error message for the user
	 */
	public String getMisstep(String property) {
		return missteps.get(property);
	}

	/**
	 * Checks whether any property has a misstep.
	 * 
	 * @return true, if there is at least one misstep
	 */
	public boolean hasAnyMissteps() {
		return !missteps.isEmpty();
	}

	/**
	 * Checks whether the misstep for the given property of the registration exists.
	 * 
	 * @param property
	 *            whose misstep it checks
	 * @return true, if the misstep exists
	 */
	public boolean hasMisstep(String property) {
		return missteps.containsKey(property);
	}

	/**
	 * Fills this form based on the request parameters.
	 * 
	 * @param req
	 *            request
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.nick = Util.prepareString(req.getParameter("nick"));
		this.password = Util.prepareString(req.getParameter("password"));
	}

	/**
	 * Auxiliary method used to check the properties of the login.
	 */
	public void validate() {
		missteps.clear();
		if (this.nick.isEmpty()) {
			missteps.put("nick", "Nickname is mandatory!");
		} else if (!Util.userExists(this.nick)) {
			missteps.put("nick", "No such user.");
			return;
		}

		if (this.password.isEmpty()) {
			missteps.put("password", "Password is mandatory!");
		} else if (!Util.checkLogin(this.nick, this.password)) {
			missteps.put("password", "Incorrect password.");
		}
	}

	/**
	 * Getter for the users nickname.
	 * 
	 * @return user nickname
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for the users nickname.
	 * 
	 * @param nick
	 *            user nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for the password of the user.
	 * 
	 * @return password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for the password of the user.
	 * 
	 * @param password
	 *            of the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for the password hash of the user.
	 * 
	 * @return password hash of the user
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter for the password hash of the user.
	 * 
	 * @param passwordHash
	 *            password hash of the user
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
