package uzblog.modules.blog.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "referer")
public class Referer implements Serializable {

	private static final long serialVersionUID = 2916757379109548980L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * host
	 */
	@Column(name = "host", nullable = false, unique = true)
	private String host;

	/**
	 * referer
	 */
	@Column(name = "referer")
	private String referer;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
}