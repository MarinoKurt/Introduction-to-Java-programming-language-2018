package hr.fer.zemris.java.p12.model;

/**
 * Representation of a poll option, containing all the relevant information.
 * 
 * @author MarinoK
 */
public class PollOption {

	/** ID of the poll option. */
	private long id;

	/** Title of the option. */
	private String optionTitle;

	/** Options link. */
	private String optionLink;

	/** ID of the parent poll, which contains this poll option. */
	private long pollID;

	/** Counter of the votes for this option. */
	private long votesCount;

	/**
	 * Getter for the option ID.
	 * 
	 * @return ID of the option
	 */
	public long getId() {
		return id;
	}

	/**
	 * Getter for the option title.
	 * 
	 * @return option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Getter for the link of the option.
	 * 
	 * @return options link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Getter for the parent poll ID.
	 * 
	 * @return poll ID of the parent poll
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Getter for the votes count.
	 * 
	 * @return number of votes
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Setter for the option ID.
	 * 
	 * @param id
	 *            of the option
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Setter for the option title.
	 * 
	 * @param optionTitle
	 *            of the option
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Setter for the option link.
	 * 
	 * @param optionLink
	 *            of the option
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Setter for the option pollID.
	 * 
	 * @param pollID
	 *            of the parenting poll
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Setter for the votes count.
	 * 
	 * @param votesCount
	 *            of the option
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
}
