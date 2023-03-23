package gov.kcg.kgo.viewModel.commonApi.getGeneralColumn.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.commonApi.common.rs.bean.CaseColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用型查詢欄位作業 Form
 */
@ApiModel(description = "通用型查詢欄位作業 ViewForm")
public class GetGeneralColumnActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "案件欄位資料")
	List<CaseColumn> columns;

	public List<CaseColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<CaseColumn> columns) {
		this.columns = columns;
	}
}
