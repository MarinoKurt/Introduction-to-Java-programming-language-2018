package hr.fer.zemris.java.p12.model;

/**
 * Representation of a poll with all relevant information about it.
 * 
 * @author MarinoK
 */
public class Poll {

	/** ID of the poll */
	private long id;

	/** Title of the poll. */
	private String title;

	/** Message of the poll. */
	private String message;

	/**
	 * Getter for the poll id.
	 * 
	 * @return poll ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * Getter for the title.
	 * 
	 * @return poll title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Getter for the message.
	 * 
	 * @return poll message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for the poll ID.
	 * 
	 * @param id
	 *            of the poll
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Setter for the title.
	 * 
	 * @param title
	 *            of the poll
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Setter for the message.
	 * 
	 * @param message
	 *            of the poll
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
