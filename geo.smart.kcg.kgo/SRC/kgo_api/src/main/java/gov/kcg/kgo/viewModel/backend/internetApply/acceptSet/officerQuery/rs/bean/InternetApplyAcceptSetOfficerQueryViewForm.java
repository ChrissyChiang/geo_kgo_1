package gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerQuery.rs.bean;

import java.util.List;

import gov.kcg.kgo.dto.AcceptSetOfficerQueryDto;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-受理機關設定-承辦人查詢 View Form
 */
@ApiModel(description = "網路申辦-受理機關設定-承辦人查詢 View Form")
public class InternetApplyAcceptSetOfficerQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 承辦人清單 **/
	@ApiModelProperty(value = "承辦人清單 ")
	private List<AcceptSetOfficerQueryDto> grid;

	public List<AcceptSetOfficerQueryDto> getGrid() {
		return grid;
	}

	public void setGrid(List<AcceptSetOfficerQueryDto> grid) {
		this.grid = grid;
	}

}
