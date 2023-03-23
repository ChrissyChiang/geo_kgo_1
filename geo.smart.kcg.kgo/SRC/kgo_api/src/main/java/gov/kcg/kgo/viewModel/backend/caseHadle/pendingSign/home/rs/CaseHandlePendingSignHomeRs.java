package gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.home.rs;


import gov.kcg.kgo.viewModel.backend.caseHadle.pendingSign.home.rs.bean.CaseHandlePendingSignHomeViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待簽收匣-初始畫面 rs
 */
@ApiModel(description = "後台案件處理-待簽收匣-初始畫面 rs")
public class CaseHandlePendingSignHomeRs extends ApiBaseResponse<CaseHandlePendingSignHomeViewForm> {

	/** 機關代碼 */
	@ApiModelProperty(notes = "機關代碼")
	private String organId;

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}
