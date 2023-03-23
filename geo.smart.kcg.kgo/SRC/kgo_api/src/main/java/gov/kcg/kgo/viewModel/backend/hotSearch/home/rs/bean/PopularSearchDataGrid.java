package gov.kcg.kgo.viewModel.backend.hotSearch.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 熱門搜尋-初始畫面-資料排序集合
 */
@ApiModel(description = "熱門搜尋-初始畫面-資料排序集合")
public class PopularSearchDataGrid {

	/** 序號 */
	@ApiModelProperty(notes = "序號")
	private Integer seq;

	/** 案件種類ID */
	@ApiModelProperty(notes = "關鍵字")
	private String keyword;

	/** 統一回傳'1' */
	@ApiModelProperty(notes = "統一回傳'1'")
	private String showEdit;

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

	public String getShowEdit() {
		return showEdit;
	}

	public void setShowEdit(String showEdit) {
		this.showEdit = showEdit;
	}

}
