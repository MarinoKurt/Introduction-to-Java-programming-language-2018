package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Form used to check whether the registration is complete, and suitable for
 * storing into the database.
 * 
 * @author MarinoK
 */
public class RegistrationForm {

	/** First name of the user. */
	private String firstName;

	/** Last name of the user. */
	private String lastName;

	/** User nickname. */
	private String nick;

	/** User e-mail address. */
	private String email;

	/** User password. */
	private String password;

	/** Hash of the user password. */
	private String passwordHash;

	/** Map of missteps user made while filling the form. */
	Map<String, String> missteps = new HashMap<>();

	/** Default constructor of the form. */
	public RegistrationForm() {
	}

	/**
	 * Fetches the misstep for the given property of the registration.
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
		this.firstName = Util.prepareString(req.getParameter("firstName"));
		this.lastName = Util.prepareString(req.getParameter("lastName"));
		this.nick = Util.prepareString(req.getParameter("nick"));
		this.email = Util.prepareString(req.getParameter("email"));
		this.password = Util.prepareString(req.getParameter("password"));
	}

	/**
	 * Auxiliary method used to fill form data into the user construct.
	 * 
	 * @param user
	 *            to fill
	 */
	public void fillIntoUser(BlogUser user) {
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setNick(this.nick);
		user.setEmail(this.email);
		user.setPasswordHash(Util.calculateHash(this.password));
	}

	/**
	 * Auxiliary method used to check the properties of the registration.
	 */
	public void validate() {
		missteps.clear();

		if (this.firstName.isEmpty()) {
			missteps.put("firstName", "First name is mandatory!");
		}

		if (this.lastName.isEmpty()) {
			missteps.put("lastName", "Last name is mandatory!");
		}

		if (this.email.isEmpty()) {
			missteps.put("email", "Email address is mandatory!");
		} else {
			if (!Util.checkEmail(this.email)) {
				missteps.put("email", "Invalid email address. Must be of form a@b.c");
			}
		}

		if (!Util.checkPassword(this.password)) {
			missteps.put("password", "Password must be at least 8 characters long.");
		}

		if (this.nick.isEmpty()) {
			missteps.put("nick", "Nickname is mandatory!");
		} else {
			if (Util.userExists(this.nick)) {
				missteps.put("nick", "Nickname already exists. Try again.");
			}
		}
	}

	/**
	 * Getter for the users first name.
	 * 
	 * @return first name of the user
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for the users first name.
	 * 
	 * @param firstName
	 *            of the user
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for the users last name.
	 * 
	 * @return last name of the user
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for the users last name.
	 * 
	 * @param lastName
	 *            of the user
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * Getter for the email.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for the email.
	 * 
	 * @param email address
	 * 
	 */
	public void setEmail(String email) {
		this.email = email;
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

	/**
	 * Getter for the missteps of the user.
	 * 
	 * @return map of missteps
	 */
	public Map<String, String> getMissteps() {
		return missteps;
	}

	/**
	 * Setter for the missteps of the user.
	 * 
	 * @param missteps
	 *            map of missteps
	 */
	public void setMissteps(Map<String, String> missteps) {
		this.missteps = missteps;
	}

}
