package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GeneratorType;


/**
 * The persistent class for the KGO_CASESET_FORM database table.
 * 
 */
@Entity
@Table(name="KGO_CASESET_FORM")
@NamedQuery(name="KgoCasesetForm.findAll", query="SELECT k FROM KgoCasesetForm k")
public class KgoCasesetForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Seq", unique = true, nullable = false)
	private Integer seq;
	
	@Column(name = "CaseSetId", unique = true, nullable = false, length = 30)
	private String caseSetId;

	@Column(name="FileName", length=500)
	private String fileName;

	@Column(name="Title")
	private String title;

	@Column(name="Type", nullable=false, length=50)
	private String type;

	public KgoCasesetForm() {
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}