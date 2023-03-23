package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.model.KgoCasesetMemo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "申辦說明頁-說明頁資料集合")
@Entity
public class CasesetMemoAndCasesetQueryDto {

	@Id
	@ApiModelProperty(notes = "序號")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Seq", insertable = false, updatable = false, unique = true, nullable = false)
	private Integer seq;

	@ApiModelProperty(notes = "案件種類ID")
	@Column(name = "CaseSetId", unique = true, nullable = false, length = 30)
	private String caseSetId;

	@ApiModelProperty(notes = "申辦類型")
	@Column(name = "ApplyType", unique = true, nullable = false, length = 30)
	private String applyType;

	@ApiModelProperty(notes = "標題")
	@Column(name = "Title")
	private String title;

	@ApiModelProperty(notes = "內容")
	@Column(name = "ContentHtml")
	private String contentHtml;

	@ApiModelProperty(notes = "附件")
	@Column(name = "FileName")
	private String fileName;

	public CasesetMemoAndCasesetQueryDto() {
	}

	public CasesetMemoAndCasesetQueryDto(KgoCasesetMemo cm, KgoCaseset c) {
		this.seq = cm.getSeq();
		this.caseSetId = cm.getCaseSetId();
		this.applyType = cm.getApplyType();
		this.title = cm.getTitle();
		this.contentHtml = cm.getContentHtml();
		this.fileName = cm.getFileName();
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

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
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
