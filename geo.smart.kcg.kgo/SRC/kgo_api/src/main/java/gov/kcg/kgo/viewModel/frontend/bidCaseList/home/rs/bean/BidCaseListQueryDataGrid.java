package gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申辦案件清單-申辦案件資料清單
 */
@ApiModel(description = "申辦案件清單-申辦案件資料清單")
public class BidCaseListQueryDataGrid {

	@ApiModelProperty(notes = "案件種類ID")
	private String caseSetId;

	@ApiModelProperty(notes = "案件名稱")
	private String caseSetName;

    @ApiModelProperty(notes = "整合流程分類")
    private String caseFlowType;

	@ApiModelProperty(notes = "是否啟用[申辦類型-線上(E)]")
	private Boolean isApplyTypeEActive;

	@ApiModelProperty(notes = "是否啟用[申辦類型-臨櫃(C)]")
	private Boolean isApplyTypeCActive;

	@ApiModelProperty(notes = "是否啟用[申辦類型-書表(L)]")
	private Boolean isApplyTypeLActive;

	/** 20221011 add 查詢服務外部連結 */
	@ApiModelProperty(notes = "查詢服務-外部連結")
	private String linkUrl;

	@ApiModelProperty(notes = "服務類別")
	private String categroy;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public Boolean getIsApplyTypeEActive() {
		return isApplyTypeEActive;
	}

	public void setIsApplyTypeEActive(Boolean isApplyTypeEActive) {
		this.isApplyTypeEActive = isApplyTypeEActive;
	}

	public Boolean getIsApplyTypeCActive() {
		return isApplyTypeCActive;
	}

	public void setIsApplyTypeCActive(Boolean isApplyTypeCActive) {
		this.isApplyTypeCActive = isApplyTypeCActive;
	}

	public Boolean getIsApplyTypeLActive() {
		return isApplyTypeLActive;
	}

	public void setIsApplyTypeLActive(Boolean isApplyTypeLActive) {
		this.isApplyTypeLActive = isApplyTypeLActive;
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

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getCategroy() {return categroy;}

	public void setCategroy(String categroy) {this.categroy = categroy;}
}
