package gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitQuery.rs.bean;

import java.util.List;

import gov.kcg.kgo.dto.AcceptSetUnitQueryDto;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-受理機關設定-受理機關查詢 View Form
 */
@ApiModel(description = "網路申辦-受理機關設定-受理機關查詢 View Form")
public class InternetApplyAcceptSetUnitQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 受理機關設定清單 **/
	@ApiModelProperty(value = "受理機關設定清單")
	private List<AcceptSetUnitQueryDto> grid;

	public List<AcceptSetUnitQueryDto> getGrid() {
		return grid;
	}

	public void setGrid(List<AcceptSetUnitQueryDto> grid) {
		this.grid = grid;
	}

}
