package gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211109 add 機關審核表單
 * 網路申辦-表單設定- 機關審核表單欄位群組維護-進版儲存 rq
 */
@ApiModel(description = "網路申辦-表單設定- 機關審核表單欄位群組維護-進版儲存 rq")
public class InternetApplyFormSetOrganGroupColumnSaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "服務案件編號",required = true)
	private String caseSetId;

	@ApiModelProperty(notes = "群組欄位資料", required = true)
	private List<OrganGroupColumnData> groupColumnSet;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public List<OrganGroupColumnData> getGroupColumnSet() {
		return groupColumnSet;
	}

	public void setGroupColumnSet(List<OrganGroupColumnData> groupColumnSet) {
		this.groupColumnSet = groupColumnSet;
	}
}
