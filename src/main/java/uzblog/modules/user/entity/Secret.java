package uzblog.modules.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 密保
 */
@Entity
@Table(name = "secret")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Secret {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "user_id")
	private long userId; // 用户ID

	@Column(nullable = false)
	private String question;

	@Column(nullable = false)
	private String answer;

	@Column(name = "created", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date created; // 创建时间

	/**
	 * 新增逻辑删除字段，0：未删除，1：已删除
	 */
	@Column(name = "deleted", columnDefinition = "int DEFAULT 0")
	private int deleted = 0;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
}
