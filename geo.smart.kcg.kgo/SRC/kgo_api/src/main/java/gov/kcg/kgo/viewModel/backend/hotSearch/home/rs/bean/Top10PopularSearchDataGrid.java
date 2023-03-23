package gov.kcg.kgo.viewModel.backend.hotSearch.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 熱門搜尋-初始畫面-資料排序集合
 */
@ApiModel(description = "熱門搜尋-初始畫面-資料排序集合")
public class Top10PopularSearchDataGrid {

	/** 序號 */
	@ApiModelProperty(notes = "序號")
	private Integer seq;

	/** 案件種類ID */
	@ApiModelProperty(notes = "關鍵字")
	private String keyword;

	/** 統計數量 */
	@ApiModelProperty(notes = "統計數量")
	private String statistics;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getStatistics() {
		return statistics;
	}

	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}

}
