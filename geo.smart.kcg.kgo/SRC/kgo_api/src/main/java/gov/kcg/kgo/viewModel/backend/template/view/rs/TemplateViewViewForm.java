package gov.kcg.kgo.viewModel.backend.template.view.rs;

import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.bean.InternetApplyFormSetHomeDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台表單維護– 明細 View Form
 */
@ApiModel(description = "後台表單維護– 明細 View Form")
public class TemplateViewViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "表單名稱")
	private String name;

	/** 案件表單設定所有群組欄位資料集合 **/
	@ApiModelProperty(value = "案件表單設定所有群組欄位資料集合")
	private List<TemplateViewColumnDataGrid> grid;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TemplateViewColumnDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<TemplateViewColumnDataGrid> grid) {
		this.grid = grid;
	}
}
