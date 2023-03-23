package gov.kcg.kgo.viewModel.frontend.hotSearch.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 熱門查詢-查詢結果-資料集合
 */
@ApiModel(description = "熱門查詢-查詢結果-資料集合")
public class HotSearchQueryDataGrid {

	/** 案件編號 */
	@ApiModelProperty(notes = "案件編號")
	private String caseSetId;

	/** 案件名稱 */
	@ApiModelProperty(notes = "案件名稱")
	private String caseSetName;

	/** 整合流程分類 */
	@ApiModelProperty(notes = "整合流程分類")
	private String caseFlowType;

	/** 標題 */
	@ApiModelProperty(notes = "標題")
	private String title;

	/** 內容 */
	@ApiModelProperty(notes = "內容")
	private String contentHtml;

	/** 申辦類型 */
	@ApiModelProperty(notes = "申辦類型")
	private String applyType;

	@ApiModelProperty(notes = "機關分類")
	private String organId;

	@ApiModelProperty(notes = "機關分類名稱")
	private String organName;

	@ApiModelProperty(notes = "是否啟用[申辦類型-線上(E)]")
	private Boolean isApplyTypeEActive;

	@ApiModelProperty(notes = "是否啟用[申辦類型-臨櫃(C)]")
	private Boolean isApplyTypeCActive;

	@ApiModelProperty(notes = "是否啟用[申辦類型-書表(L)]")
	private Boolean isApplyTypeLActive;

	/** GEO 20220725 add 依照使用者登入方式顯示符合的案件服務 */
	@ApiModelProperty(notes = "驗證登入方式")
	private String checkType;

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

	public Boolean getApplyTypeEActive() {
		return isApplyTypeEActive;
	}

	public void setApplyTypeEActive(Boolean applyTypeEActive) {
		isApplyTypeEActive = applyTypeEActive;
	}

	public Boolean getApplyTypeCActive() {
		return isApplyTypeCActive;
	}

	public void setApplyTypeCActive(Boolean applyTypeCActive) {
		isApplyTypeCActive = applyTypeCActive;
	}

	public Boolean getApplyTypeLActive() {
		return isApplyTypeLActive;
	}

	public void setApplyTypeLActive(Boolean applyTypeLActive) {
		isApplyTypeLActive = applyTypeLActive;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getLinkUrl() {return linkUrl;}

	public void setLinkUrl(String linkUrl) {this.linkUrl = linkUrl;}

	public String getCategroy() {return categroy;}

	public void setCategroy(String categroy) {this.categroy = categroy;}
}
