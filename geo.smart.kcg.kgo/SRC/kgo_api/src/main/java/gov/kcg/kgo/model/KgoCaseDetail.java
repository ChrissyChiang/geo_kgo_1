package gov.kcg.kgo.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the KGO_CASE_DETAIL database table.
 * 
 */
@Entity
@Table(name = "KGO_CASE_DETAIL")
@NamedQuery(name = "KgoCaseDetail.findAll", query = "SELECT k FROM KgoCaseDetail k")
public class KgoCaseDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCaseDetailPK id;

	@Column(name = "ColumnValue")
	private String columnValue;

	@Column(name = "CorrectMemo")
	private String correctMemo;

	@Column(name = "CorrectBValue")
	private String correctBValue;

	@Column(name = "IsCorrect")
	private Boolean isCorrect;

	@Column(name = "IsSource")
	private Boolean isSource;

	public KgoCaseDetail() {
	}

	public KgoCaseDetailPK getId() {
		return this.id;
	}

	public void setId(KgoCaseDetailPK id) {
		this.id = id;
	}

	public String getColumnValue() {
		return this.columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public String getCorrectMemo() {
		return this.correctMemo;
	}

	public void setCorrectMemo(String correctMemo) {
		this.correctMemo = correctMemo;
	}

	public String getCorrectBValue() {
		return correctBValue;
	}

	public void setCorrectBValue(String correctBValue) {
		this.correctBValue = correctBValue;
	}

	public Boolean getIsCorrect() {
		return this.isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public Boolean getIsSource() {
		return this.isSource;
	}

	public void setIsSource(Boolean isSource) {
		this.isSource = isSource;
	}
}