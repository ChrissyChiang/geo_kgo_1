package gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.review.rs.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean.CaseHandleCaseViewSaCaseHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.home.rs.bean.CaseHandleDetailCasesetGroup;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待簽收匣-案件查詢 View Form
 */
@ApiModel(description = "後台案件處理-待簽收匣-案件檢視View Form")
public class CaseHandlePendingSignReviewViewForm extends CaseHandleCaseViewSaCaseHomeViewForm {

	private static final long serialVersionUID = 1L;

}
