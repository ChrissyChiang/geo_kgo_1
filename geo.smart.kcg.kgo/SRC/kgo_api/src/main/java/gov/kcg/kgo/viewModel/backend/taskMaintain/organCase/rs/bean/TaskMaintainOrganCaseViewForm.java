package gov.kcg.kgo.viewModel.backend.taskMaintain.organCase.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任務及公告管理– 機關帶出案件View Form
 */
@ApiModel(description = "任務及公告管理– 機關帶出案件View Form")
public class TaskMaintainOrganCaseViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 服務案件-案件 **/
	@ApiModelProperty(value = "服務案件-案件")
	private ComboBox caseNameOptions;

	/** 活動項目下拉式選單 **/
	@ApiModelProperty(value = "活動項目下拉式選單")
	private ComboBox activityItemOptions;

	public ComboBox getCaseNameOptions() {
		return caseNameOptions;
	}

	public void setCaseNameOptions(ComboBox caseNameOptions) {
		this.caseNameOptions = caseNameOptions;
	}

	public ComboBox getActivityItemOptions() {
		return activityItemOptions;
	}

	public void setActivityItemOptions(ComboBox activityItemOptions) {
		this.activityItemOptions = activityItemOptions;
	}

}
