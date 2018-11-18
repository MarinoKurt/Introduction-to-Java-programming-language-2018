package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Form used to check whether the comment is complete, and suitable for storing
 * into the database.
 * 
 * @author MarinoK
 */
public class CommentForm {

	/** Message of the comment. */
	private String message;
	/** Email of the author of the comment. */
	private String email;

	/** Map of missteps user made while filling the form. */
	Map<String, String> missteps = new HashMap<>();

	/**
	 * Default constructor for the CommentForm.
	 */
	public CommentForm() {
	}

	/**
	 * Fetches the misstep for the given property of the comment.
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
	 * Checks whether the misstep for the given property of the comment exists.
	 * 
	 * @param property
	 *            whose misstep it checks
	 * @return true, if the misstep exists
	 */
	public boolean hasMisstep(String property) {
		return missteps.containsKey(property);
	}

	/**
	 * Fills the form from the request parameters.
	 * 
	 * @param req
	 *            containing the parameters
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.message = Util.prepareString(req.getParameter("message"));
		this.email = req.getParameter("email");
	}

	/**
	 * Auxiliary method used to check the properties of the comment.
	 */
	public void validate() {
		missteps.clear();

		if (this.message.isEmpty()) {
			missteps.put("message", "Message is mandatory!");
		}

		if (this.email == null) return;

		if (this.email.isEmpty()) {
			missteps.put("email", "Email address is mandatory!");
		} else {
			if (!Util.checkEmail(this.email)) {
				missteps.put("email", "Invalid email address.");
			}
		}
	}

	/**
	 * Getter for the message of the comment.
	 * 
	 * @return message of the comment
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for the message of the comment.
	 * 
	 * @param message
	 *            of the comment
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for the email of the comment author.
	 * 
	 * @return email of the comment author
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for the email of the comment author.
	 * 
	 * @param email
	 *            of the comment author
	 * 
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
