package gov.kcg.kgo.viewModel.backend.counterApply.save.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.counterApply.home.rs.bean.CounterApplyHomeDataGrid;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "臨櫃申請-申請說明資料儲存 rq")
public class CounterApplySaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "服務案件編號")
	private String caseSetId;

	/** 臨櫃申請資料集合 */
	@ApiModelProperty(value = "臨櫃申請資料集合")
	private List<CounterApplyHomeDataGrid> grid;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public List<CounterApplyHomeDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<CounterApplyHomeDataGrid> grid) {
		this.grid = grid;
	}

}
