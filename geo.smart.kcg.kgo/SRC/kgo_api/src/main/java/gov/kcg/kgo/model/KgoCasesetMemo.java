package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the KGO_CASESET_MEMO database table.
 * 
 */
@Entity
@Table(name = "KGO_CASESET_MEMO")
@NamedQuery(name = "KgoCasesetMemo.findAll", query = "SELECT k FROM KgoCasesetMemo k")
public class KgoCasesetMemo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Seq", insertable = false, updatable = false, unique = true, nullable = false)
	private Integer seq;
	
	@Column(name = "CaseSetId", unique = true, nullable = false, length = 30)
	private String caseSetId;

	@Column(name = "ApplyType", unique = true, nullable = false, length = 30)
	private String applyType;

	@Column(name = "Title")
	private String title;

	@Column(name = "ContentHtml")
	private String contentHtml;

	@Column(name = "FileName")
	private String fileName;

	public KgoCasesetMemo() {
	}

	public Integer getSeq() {
		return seq;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}


	public String getApplyType() {
		return applyType;
	}


	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}


	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentHtml() {
		return contentHtml;
	}

	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}