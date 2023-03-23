package gov.kcg.kgo.viewModel.backend.common.organSelect.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 共用-機關列表Popup-資料查詢
 */
@ApiModel(description = "共用-機關列表Popup-資料查詢")
public class OrganSelectQueryDataGrid {

	/** 機關代碼 */
	@ApiModelProperty(notes = "機關代碼")
	private String organId;

	/** 機關名稱 */
	@ApiModelProperty(notes = "機關名稱")
	private String organName;

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

}
