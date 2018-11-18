package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class represents a blog comment.
 * 
 * @author MarinoK
 */
@Entity
@Table(name = "blog_comments")
@Cacheable(true)
public class BlogComment {

	/** Generated comment ID. */
	private Long id;

	/** Blog entry whose comment this is. */
	private BlogEntry blogEntry;

	/** E-mail address of the comment author. */
	private String usersEMail;

	/** Content of the comment. */
	private String message;

	/** Time of the comment. */
	private Date postedOn;

	/**
	 * Getter for the comment ID.
	 * 
	 * @return ID of the comment.
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter for the commment ID.
	 * 
	 * @param id
	 *            to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for the blog entry whose comment this is.
	 * 
	 * @return parent blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Setter for the blog entry.
	 * 
	 * @param blogEntry
	 *            parent blog entry to be set
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter for the users email address.
	 * 
	 * @return users email address
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter for the users email address.
	 * 
	 * @param usersEMail
	 *            address of the user
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter for the comment message.
	 * 
	 * @return message of the comment
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for the comment message.
	 * 
	 * @param message
	 *            of the comment
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for the timestamp when the comment was posted.
	 * 
	 * @return timestamp when the comment was posted
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter for the timestamp when the comment was posted.
	 * 
	 * @param postedOn
	 *            timestamp when the comment was posted
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}
}