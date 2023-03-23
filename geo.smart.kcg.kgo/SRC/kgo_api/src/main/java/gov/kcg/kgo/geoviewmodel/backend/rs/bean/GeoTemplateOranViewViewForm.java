package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoTemplateOrganViewColumnDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211108 add for 機關審核表單
 * 後台表單維護–取得機關審核表單初始欄位明細 View Form
 */
@ApiModel(description = "後台表單維護–取得機關審核表單初始欄位明細 View Form")
public class GeoTemplateOranViewViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "表單名稱")
	private String name;

	@ApiModelProperty(value = "案件表單設定所有群組欄位資料集合")
	private List<GeoTemplateOrganViewColumnDataGrid> grid;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GeoTemplateOrganViewColumnDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<GeoTemplateOrganViewColumnDataGrid> grid) {
		this.grid = grid;
	}
}
