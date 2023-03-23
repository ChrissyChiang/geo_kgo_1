package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_CASE_FWD database table.
 * 
 */
@Entity
@Table(name="KGO_CASE_FWD")
@NamedQuery(name="KgoCaseFwd.findAll", query="SELECT k FROM KgoCaseFwd k")
public class KgoCaseFwd implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCaseFwdPK id;

	@Column(name="FileName", length=500)
	private String fileName;

	@Column(name="Title")
	private String title;

	public KgoCaseFwd() {
	}

	public KgoCaseFwdPK getId() {
		return this.id;
	}

	public void setId(KgoCaseFwdPK id) {
		this.id = id;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}