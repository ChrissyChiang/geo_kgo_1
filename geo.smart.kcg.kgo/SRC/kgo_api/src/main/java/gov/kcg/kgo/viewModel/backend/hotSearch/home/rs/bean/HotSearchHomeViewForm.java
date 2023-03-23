package gov.kcg.kgo.viewModel.backend.hotSearch.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務申請-初始畫面View Form
 */
@ApiModel(description = "服務申請-初始畫面View Form")
public class HotSearchHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 設定的熱門搜尋 **/
	@ApiModelProperty(value = "設定的熱門搜尋")
	private List<PopularSearchDataGrid> popularSearchGrid;

	/** 每月前10名熱門搜尋 **/
	@ApiModelProperty(value = "每月前10名熱門搜尋")
	private List<Top10PopularSearchDataGrid> top10PopularSearchGrid;

	public List<PopularSearchDataGrid> getPopularSearchGrid() {
		return popularSearchGrid;
	}

	public void setPopularSearchGrid(List<PopularSearchDataGrid> popularSearchGrid) {
		this.popularSearchGrid = popularSearchGrid;
	}

	public List<Top10PopularSearchDataGrid> getTop10PopularSearchGrid() {
		return top10PopularSearchGrid;
	}

	public void setTop10PopularSearchGrid(List<Top10PopularSearchDataGrid> top10PopularSearchGrid) {
		this.top10PopularSearchGrid = top10PopularSearchGrid;
	}

}
