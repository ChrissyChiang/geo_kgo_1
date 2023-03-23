package gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.dto.AccountManagementQueryDto;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 共用-機關科室人員查詢 View Form
 */
@ApiModel(description = "共用-機關科室人員查詢 View Form")
public class OrganUnitUserSelectQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 機關科室所屬的人員清單 **/
	@ApiModelProperty(value = "列出該機關科室所屬的人員清單")
	private List<AccountManagementQueryDto> grid;

	public List<AccountManagementQueryDto> getGrid() {
		return grid;
	}

	public void setGrid(List<AccountManagementQueryDto> grid) {
		this.grid = grid;
	}

}
