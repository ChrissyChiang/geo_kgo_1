package gov.kcg.kgo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@ApiModel(description = "案件查詢集合")
@Entity
public class CaseProcessSearchDto {

	/** 申請日期 */
	@ApiModelProperty(notes = "申請日期")
	@Column(name = "APPLY_DATE")
	private Date applyDate;

	/** 案件編號 */
	@ApiModelProperty(notes = "案件編號")
	@Column(name = "CASE_ID")
	@Id
	private String caseId;

	/** 案件名稱 */
	@ApiModelProperty(notes = "案件名稱")
	@Column(name = "CASESET_NAME")
	private String caseSetName;

	/** 狀態 */
	@ApiModelProperty(notes = "狀態")
	@Column(name = "STATUS")
	private String status;

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
