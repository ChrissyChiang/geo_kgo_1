package gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaSave.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaSave.rq.bean.InternetApplyAcceptSetAreaSaveDataGrid;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-受理機關設定-受理區機關新增 rq")
public class InternetApplyAcceptSetAreaSaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "服務案件編號")
	private String caseSetId;

	@ApiModelProperty(notes = "受理區機關儲存資料集合")
	private List<InternetApplyAcceptSetAreaSaveDataGrid> grid;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public List<InternetApplyAcceptSetAreaSaveDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<InternetApplyAcceptSetAreaSaveDataGrid> grid) {
		this.grid = grid;
	}

}
