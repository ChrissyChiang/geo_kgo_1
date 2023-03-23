package gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台 - 軌跡紀錄 - 查詢 - 身分證驗證統計
 */
@ApiModel(description = "後台 - 軌跡紀錄 - 查詢 - 身分證驗證統計")
public class LogLoginTypeCount {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "身份驗證類型")
	private String loginType;

	@ApiModelProperty(value = "數量")
	private Integer count;

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
