package gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211115 add 非市府員工登入後台
 * 服務申請-權限開通申請-初始畫面-審核者列表 View Form
 */
@ApiModel(description = "服務申請-權限開通申請-初始畫面-審核者列表 View Form")
public class ServiceApplyPermissionUserComboBoxActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "審核主管")
	private ComboBox reviewerComboBox;

	@ApiModelProperty(value = "系統管理者主管")
	private ComboBox reviewer2ComboBox;

	public ComboBox getReviewerComboBox() {
		return reviewerComboBox;
	}

	public void setReviewerComboBox(ComboBox reviewerComboBox) {
		this.reviewerComboBox = reviewerComboBox;
	}

	public ComboBox getReviewer2ComboBox() {
		return reviewer2ComboBox;
	}

	public void setReviewer2ComboBox(ComboBox reviewer2ComboBox) {
		this.reviewer2ComboBox = reviewer2ComboBox;
	}
}
