package gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台 - 軌跡紀錄 - 查詢 - 每日結案統統計分析
 */
@ApiModel(description = "後台 - 軌跡紀錄 - 查詢 - 每日結案統計分析")
public class CasesFinishCount {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "結案日期")
	private String finishDate;


	@ApiModelProperty(value = "數量")
	private Integer count;

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
