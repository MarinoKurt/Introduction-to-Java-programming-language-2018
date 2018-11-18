package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Form used to check whether the entry is complete, and suitable for storing
 * into the database.
 * 
 * @author MarinoK
 */
public class EntryForm {

	/** Title of the entry. */
	private String title;

	/** Text of the form. */
	private String text;

	/** Map of missteps user made while filling the form. */
	Map<String, String> missteps = new HashMap<>();

	/** Default constructor of the form. */
	public EntryForm() {
	}

	/**
	 * Fetches the misstep for the given property of the entry.
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
	 * Checks whether the misstep for the given property of the entry exists.
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
		this.title = Util.prepareString(req.getParameter("title"));
		this.text = Util.prepareString(req.getParameter("text"));
	}

	/**
	 * Creates a entry form from the given entry.
	 * 
	 * @param entry
	 *            source of data
	 */
	public void createFromEntry(BlogEntry entry) {
		this.text = entry.getText();
		this.title = entry.getTitle();
	}

	/**
	 * Auxiliary method used to fill form data into the entry construct.
	 * 
	 * @param entry
	 *            to fill
	 */
	public void fillIntoEntry(BlogEntry entry) {
		entry.setText(this.text);
		entry.setTitle(this.title);
	}

	/**
	 * Auxiliary method used to check the properties of the entry.
	 */
	public void validate() {
		missteps.clear();

		if (this.title.isEmpty()) {
			missteps.put("title", "Title is mandatory!");
		}
		if (this.text.isEmpty()) {
			missteps.put("text", "Text is mandatory!");
		}
	}

	/**
	 * Getter for the title.
	 * 
	 * @return title of the entry
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for the title.
	 * 
	 * @param title
	 *            of the entry
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for the text.
	 * 
	 * @return text of the entry
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter for the text.
	 * 
	 * @param text
	 *            of the entry
	 */
	public void setText(String text) {
		this.text = text;
	}

}
