package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCorrectRecord;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean.CaseHandleCaseViewSaCaseHomeViewForm;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待審核匣-補正作業-初始畫面 View Form
 */
@ApiModel(description = "後台案件處理-待審核匣-補正作業-初始畫面 View Form")
public class CaseHandlePendingReviewCorrectHomeViewForm extends CaseHandleCaseViewSaCaseHomeViewForm {

	private static final long serialVersionUID = 1L;

	/** 補正紀錄清單 **/
	@ApiModelProperty(value = "補正紀錄清單")
	private List<CaseHandlePendingReviewCorrectHomeViewRecordForm> recordForms;

	public List<CaseHandlePendingReviewCorrectHomeViewRecordForm> getRecordForms() {
		return recordForms;
	}

	public void setRecordForms(List<CaseHandlePendingReviewCorrectHomeViewRecordForm> recordForms) {
		this.recordForms = recordForms;
	}
}
