package gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitSave.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-受理機關設定-受理機關新增 rq")
public class InternetApplyAcceptSetUnitSaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "服務案件編號")
	private String caseSetId;

	@ApiModelProperty(value = "機關代碼清單")
	private List<String> grid;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public List<String> getGrid() {
		return grid;
	}

	public void setGrid(List<String> grid) {
		this.grid = grid;
	}

}
