package gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台 - 軌跡紀錄 - 查詢 - 登入/登出時間統計
 */
@ApiModel(description = "後台 - 軌跡紀錄 - 查詢 - 登入/登出時間統計")
public class LogLoginLogoutCount {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "紀錄日期")
	private String logDate;

	@ApiModelProperty(value = "數量")
	private Integer count;

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
