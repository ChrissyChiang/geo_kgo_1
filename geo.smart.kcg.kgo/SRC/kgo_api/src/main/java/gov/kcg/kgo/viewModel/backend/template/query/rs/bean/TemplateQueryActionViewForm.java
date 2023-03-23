package gov.kcg.kgo.viewModel.backend.template.query.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台表單維護– 查詢 View Form
 */
@ApiModel(description = "後台表單維護– 查詢 View Form")
public class TemplateQueryActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 資料集 **/
	@ApiModelProperty(value = "資料集")
	private List<TemplateQueryActionDataGrid> templateQueryActionDataGrids;

	public List<TemplateQueryActionDataGrid> getTemplateQueryActionDataGrids() {
		return templateQueryActionDataGrids;
	}

	public void setTemplateQueryActionDataGrids(List<TemplateQueryActionDataGrid> templateQueryActionDataGrids) {
		this.templateQueryActionDataGrids = templateQueryActionDataGrids;
	}
}
