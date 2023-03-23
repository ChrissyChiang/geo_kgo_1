package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAcceptHome.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待審核匣-取消簽收 初始畫面 View Form
 */
@ApiModel(description = "後台案件處理-待審核匣-取消簽收 初始畫面 View Form")
public class CaseHandlePendingReviewCancelAcceptHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/**
	 * type comboBox (只有機關/承辦兩個選項)
	 */
	@ApiModelProperty(value = "固定選項的comboBox元件")
	private ComboBox organCaseOfficerComboBox;

	public ComboBox getOrganCaseOfficerComboBox() {
		return organCaseOfficerComboBox;
	}

	public void setOrganCaseOfficerComboBox(ComboBox organCaseOfficerComboBox) {
		this.organCaseOfficerComboBox = organCaseOfficerComboBox;
	}

}
