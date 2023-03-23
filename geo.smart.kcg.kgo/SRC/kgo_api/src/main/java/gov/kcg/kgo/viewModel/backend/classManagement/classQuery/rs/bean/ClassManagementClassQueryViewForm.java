package gov.kcg.kgo.viewModel.backend.classManagement.classQuery.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 分類維護功能-主畫面搜尋 View Form
 */
@ApiModel(description = "分類維護功能-主畫面搜尋 View Form")
public class ClassManagementClassQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 分類搜尋結果清單 **/
	@ApiModelProperty(value = "分類搜尋結果清單")
	private List<ClassManagementClassQueryDataGrid> grid;

	public List<ClassManagementClassQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<ClassManagementClassQueryDataGrid> grid) {
		this.grid = grid;
	}

}
	