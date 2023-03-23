package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoBidCaseListQueryDataGridModel;
import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireCaseSetModel;
import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireCaseSetTopicModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.bean.BidCaseListQueryDataGrid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20210814 add
 *
 * 申辦案件資料查詢 ViewForm
 */
@ApiModel(description = "申辦案件資料查詢  ViewForm")
public class GeoCaseSetApplyRankDetailListViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 申辦案件資料查詢集合 **/
	@ApiModelProperty(value = "申辦案件資料查詢集合")
	private List<GeoBidCaseListQueryDataGridModel> grid;

	public List<GeoBidCaseListQueryDataGridModel> getGrid() {
		return grid;
	}

	public void setGrid(List<GeoBidCaseListQueryDataGridModel> grid) {
		this.grid = grid;
	}
}
