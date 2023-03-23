package gov.kcg.kgo.viewModel.backend.accountManagement.accountQuery.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 帳號權限管理-帳號搜尋 View Form
 */
@ApiModel(description = "帳號權限管理-帳號搜尋 View Form")
public class AccountManagementQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "帳號權限管理-列出搜尋結果資料")
	private List<AccountManagementQueryDataGrid> grid;

	public List<AccountManagementQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<AccountManagementQueryDataGrid> grid) {
		this.grid = grid;
	}

}
