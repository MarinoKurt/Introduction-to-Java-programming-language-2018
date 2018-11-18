package hr.fer.zemris.java.webapp;

/**
 * JavaBean representing a band, with all relevant data for this voting
 * application.
 * 
 * @author MarinoK
 */
public class Band {

	/** ID of the band. */
	int id;

	/** Name of the band. */
	String name;

	/** Link to a representative song by the band. */
	String songLink;

	/** Number of votes this band got so far. */
	int votes;

	/**
	 * Constructor for the Band JavaBean.
	 * 
	 * @param id
	 *            of the band
	 * @param name
	 *            of the band
	 * @param songLink
	 *            of the band
	 */
	public Band(int id, String name, String songLink) {
		super();
		this.id = id;
		this.name = name;
		this.songLink = songLink;
	}

	/**
	 * @return id of the band
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return name of the band
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return song link of the band
	 */
	public String getSongLink() {
		return songLink;
	}

	/**
	 * @return votes band got so far
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * @param votes to set
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}
}
