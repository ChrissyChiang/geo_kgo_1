package gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-表單設定-欄位群組維護-進版儲存rq")
public class InternetApplyFormSetGroupColumnSaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	/** 服務案件編號 */
	@ApiModelProperty(notes = "服務案件編號")
	private String caseSetId;

	/** 群組欄位資料 */
	@ApiModelProperty(notes = "群組欄位資料")
	private List<GroupColumnData> groupColumnSet;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public List<GroupColumnData> getGroupColumnSet() {
		return groupColumnSet;
	}

	public void setGroupColumnSet(List<GroupColumnData> groupColumnSet) {
		this.groupColumnSet = groupColumnSet;
	}
}
