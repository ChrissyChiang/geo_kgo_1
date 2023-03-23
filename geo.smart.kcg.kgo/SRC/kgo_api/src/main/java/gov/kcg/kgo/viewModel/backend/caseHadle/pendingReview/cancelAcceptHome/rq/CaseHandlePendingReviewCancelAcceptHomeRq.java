package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.cancelAcceptHome.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-待審核匣-取消簽收-機關承辦選擇 初始畫面rq")
public class CaseHandlePendingReviewCancelAcceptHomeRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關")
	private String organ;

	@ApiModelProperty(value = "承辦")
	private String caseOfficer;

	/**
	 * 2選1
	 * organ = 機關
	 * caseOfficer = 承辦
	 */
	@ApiModelProperty(value = "類型代碼")
	private String type;

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getCaseOfficer() {
		return caseOfficer;
	}

	public void setCaseOfficer(String caseOfficer) {
		this.caseOfficer = caseOfficer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
