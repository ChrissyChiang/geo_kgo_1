package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.home.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-案件檢視-初始畫面 View Form
 */
@ApiModel(description = "後台案件處理-案件檢視-初始畫面 View Form")
public class CaseHandleCaseViewHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 狀態下拉選單 **/
	@ApiModelProperty(value = "狀態下拉選單")
	private ComboBox statusComboBox;

	/** 作業流程分類下拉選單 **/
	@ApiModelProperty(value = "作業流程分類下拉選單")
	private ComboBox flowTypeComboBox;

	public ComboBox getStatusComboBox() {
		return statusComboBox;
	}

	public void setStatusComboBox(ComboBox statusComboBox) {
		this.statusComboBox = statusComboBox;
	}

	public ComboBox getFlowTypeComboBox() {
		return flowTypeComboBox;
	}

	public void setFlowTypeComboBox(ComboBox flowTypeComboBox) {
		this.flowTypeComboBox = flowTypeComboBox;
	}
}
