package gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaQuery.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-受理機關設定-受理區機關查詢 View Form
 */
@ApiModel(description = "網路申辦-受理機關設定-受理區機關查詢 View Form")
public class InternetApplyAcceptSetAreaQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 區機關清單 **/
	@ApiModelProperty(value = "區機關清單")
	private List<InternetApplyAcceptSetAreaQueryDataGrid> grid;

	public List<InternetApplyAcceptSetAreaQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<InternetApplyAcceptSetAreaQueryDataGrid> grid) {
		this.grid = grid;
	}

}
