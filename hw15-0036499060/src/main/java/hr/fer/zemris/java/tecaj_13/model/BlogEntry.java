package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class represents a blog entry.
 * 
 * @author MarinoK
 */
@NamedQueries({
		@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

	/** Generated ID of the entry. */
	private Long id;

	/** List of blog comments on this entry. */
	private List<BlogComment> comments = new ArrayList<>();

	/** Time of creation. */
	private Date createdAt;

	/** Time of last modification. */
	private Date lastModifiedAt;

	/** Title of entry. */
	private String title;

	/** Text of the entry. */
	private String text;

	/** Creator of the entry. */
	private BlogUser creator;

	/**
	 * Getter for the ID of the entry.
	 * 
	 * @return ID of the entry
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter for the ID of the entry.
	 * 
	 * @param id
	 *            of the entry
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for the comments list.
	 * 
	 * @return list of comments on this blog entry
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Setter for the comments list.
	 * 
	 * @param comments
	 *            list of comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Getter for the creation time.
	 * 
	 * @return time when the entry was created
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter for the creation time.
	 * 
	 * @param createdAt
	 *            time when the entry was created
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Getter for the last modification time.
	 * 
	 * @return time when the entry was last modified
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Setter for the last modification time.
	 * 
	 * @param lastModifiedAt
	 *            time when the entry was last modified
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Getter for the entry title.
	 * 
	 * @return title of the entry
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for the entry title.
	 * 
	 * @param title
	 *            of the entry
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for the entry text.
	 * 
	 * @return entry text
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Setter for the entry text.
	 * 
	 * @param text
	 *            of the entry
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter for the entry creator.
	 * 
	 * @return creator of the entry
	 */
	@ManyToOne
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Setter for the entry creator.
	 * 
	 * @param creator
	 *            of the entry
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}
}