package gov.kcg.kgo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@ApiModel(description = "申辦案件清單-初始畫面-申辦案件資料清單")
@Entity
public class BidCaseListQueryDto {

	@Id
	@ApiModelProperty(notes = "案件種類ID")
	@Column(name = "CASESET_ID")
	private String caseSetId;

	@ApiModelProperty(notes = "申辦類型")
	@Column(name = "APPLY_TYPE")
	private String applyType;

	@ApiModelProperty(notes = "案件名稱")
	@Column(name = "CASESET_NAME")
	private String caseSetName;

    @ApiModelProperty(notes = "CASEFLOW_TYPE")
    @Column(name = "CASEFLOW_TYPE")
    private String caseFlowType;

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

}
