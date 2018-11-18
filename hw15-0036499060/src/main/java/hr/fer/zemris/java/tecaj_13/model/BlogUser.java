package hr.fer.zemris.java.tecaj_13.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class represents a blog user.
 * 
 * @author MarinoK
 */
@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {

	/** User ID. */
	private Long id;

	/** First name of the user. */
	private String firstName;

	/** Last name of the user. */
	private String lastName;

	/** User nickname. */
	private String nick;

	/** User e-mail address. */
	private String email;

	/** Hash of the user password. */
	private String passwordHash;

	/** List of user entries. */
	private List<BlogEntry> entries;

	/**
	 * Getter for the ID of the user.
	 * 
	 * @return user ID
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter for the ID of the user.
	 * 
	 * @param id
	 *            user ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for the users first name.
	 * 
	 * @return first name of the user
	 */
	@Column(length = 50, nullable = false)
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
	@Column(length = 50, nullable = false)
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
	@Column(length = 50, nullable = false, unique = true)
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
	 * Getter for the user email.
	 * 
	 * @return user email
	 */
	@Column(length = 50, nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for the user email.
	 * 
	 * @param email
	 *            user email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for the password hash of the user.
	 * 
	 * @return password hash of the user
	 */
	@Column(nullable = false)
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
	 * Getter for the user entries.
	 * 
	 * @return entries of the user
	 */
	@OneToMany(mappedBy = "creator")
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Setter for the user entries.
	 * 
	 * @param entries
	 *            of the user
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}
