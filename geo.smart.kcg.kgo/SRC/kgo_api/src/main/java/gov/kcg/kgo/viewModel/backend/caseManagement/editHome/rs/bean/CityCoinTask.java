package gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 城市幣任務清單"
 */
@ApiModel(description = "城市幣任務清單")
public class CityCoinTask {

	/**
	 * 使用者帳號
	 */
	@ApiModelProperty(notes = "任務標題")
	private String title;

	/**
	 * 使用者名稱
	 */
	@ApiModelProperty(notes = "是否啟用")
	private boolean enable;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
