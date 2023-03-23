package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "熱門搜尋-熱門查詢集合")
@Entity
public class CasesetMemoHotSearchDto {

	/** 案件編號 */
	@ApiModelProperty(notes = "案件編號")
	@Column(name = "CASE_SET_ID")
	@Id
	private String caseSetId;

	/** 案件名稱 */
	@ApiModelProperty(notes = "案件名稱")
	@Column(name = "CASE_SET_NAME")
	private String caseSetName;

	/** 整合流程分類 */
	@ApiModelProperty(notes = "整合流程分類")
	@Column(name = "CASE_FLOW_TYPE")
	private String caseFlowType;

	/** 標題 */
	@ApiModelProperty(notes = "標題")
	@Column(name = "TITLE")
	private String title;

	/** 內容 */
	@ApiModelProperty(notes = "內容")
	@Column(name = "CONTENT_HTML")
	private String contentHtml;

	/** 申辦類型 */
	@ApiModelProperty(notes = "申辦類型")
	@Column(name = "APPLY_TYPE")
	private String applyType;

	@ApiModelProperty(notes = "機關分類")
	@Column(name = "ORGAN_ID")
	private String organId;

	@ApiModelProperty(notes = "機關分類名稱")
	@Column(name = "ORGAN_NAME")
	private String organName;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getCaseFlowType() {
		return caseFlowType;
	}

	public void setCaseFlowType(String caseFlowType) {
		this.caseFlowType = caseFlowType;
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

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

}
