package gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申辦說明頁-初始畫面 View Form
 */
@ApiModel(description = "申辦說明頁-初始畫面 View Form")
public class BidInstructionHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 案件名稱 */
	@ApiModelProperty(notes = "案件名稱")
	private String caseSetName;

	/** 是否可申辦 */
	@ApiModelProperty(notes = "是否可申辦")
	private Boolean isApply;

	/** 是否另開視窗 */
	@ApiModelProperty(notes = "是否另開視窗")
	private Boolean isOpen;

	/** 作業流程分類(ex:A,B1,B2,C1,C2,C3,..) */
	@ApiModelProperty(notes = "作業流程分類(ex:A,B1,B2,C1,C2,C3,..)")
	private String caseFlowType;

	/** 是否有對外連結 */
	@ApiModelProperty(notes = "是否有對外連結")
	private Boolean isLink;

	/** 案件受理區間(起) */
	@ApiModelProperty(notes = "案件受理區間(起)")
	private String dateStart;

	/** 案件受理區間(迄) */
	@ApiModelProperty(notes = "案件受理區間(迄)")
	private String dateEnd;

	/** 案件申辦說明資料集合(臨櫃C) **/
	@ApiModelProperty(value = "案件申辦說明資料集合(臨櫃C)")
	private BidInstructionApplyTypeCData cData;

	/** 案件申辦說明資料集合(線上E) **/
	@ApiModelProperty(value = "案件申辦說明資料集合(線上E)")
	private BidInstructionApplyTypeEData eData;

	/** 案件申辦說明資料集合(書表L) **/
	@ApiModelProperty(value = "案件申辦說明資料集合(書表L)")
	private BidInstructionApplyTypeLData lData;

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public Boolean getIsApply() {
		return isApply;
	}

	public void setIsApply(Boolean isApply) {
		this.isApply = isApply;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getCaseFlowType() {
		return caseFlowType;
	}

	public void setCaseFlowType(String caseFlowType) {
		this.caseFlowType = caseFlowType;
	}

	public Boolean getIsLink() {
		return isLink;
	}

	public void setIsLink(Boolean isLink) {
		this.isLink = isLink;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public BidInstructionApplyTypeCData getcData() {
		return cData;
	}

	public void setcData(BidInstructionApplyTypeCData cData) {
		this.cData = cData;
	}

	public BidInstructionApplyTypeEData geteData() {
		return eData;
	}

	public void seteData(BidInstructionApplyTypeEData eData) {
		this.eData = eData;
	}

	public BidInstructionApplyTypeLData getlData() {
		return lData;
	}

	public void setlData(BidInstructionApplyTypeLData lData) {
		this.lData = lData;
	}

}
