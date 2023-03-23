package gov.kcg.kgo.viewModel.backend.hotSearch.saveAll.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 熱門搜尋-熱門搜尋資料集合
 */
@ApiModel(description = "熱門搜尋-熱門搜尋資料集合")
public class HotSearchSaveDataGrid {

	/** 關鍵字 */
	@ApiModelProperty(notes = "關鍵字")
	private String keyword;

	/** 顯示順序 */
	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

}
