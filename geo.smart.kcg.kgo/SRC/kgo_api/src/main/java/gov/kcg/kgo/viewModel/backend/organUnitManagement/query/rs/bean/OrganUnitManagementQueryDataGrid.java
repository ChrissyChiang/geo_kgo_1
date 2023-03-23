package gov.kcg.kgo.viewModel.backend.organUnitManagement.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 機關科室管理-機關科室查詢
 */
@ApiModel(description = "機關科室管理-機關科室查詢")
public class OrganUnitManagementQueryDataGrid {

	/** 機關代碼 */
	@ApiModelProperty(notes = "機關代碼")
	private String organId;

	/** 機關名稱 */
	@ApiModelProperty(notes = "機關名稱")
	private String organName;

	/** 科室代碼 */
	@ApiModelProperty(notes = "科室代碼")
	private String unitId;

	/** 科室名稱 */
	@ApiModelProperty(notes = "科室名稱")
	private String unitName;

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

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}
