package gov.kcg.kgo.viewModel.backend.common.unit.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 共用-機關科室查詢-單位清單
 */
@ApiModel(description = "共用-機關科室查詢-單位清單")
public class UnitQueryDataGrid {

	/** 科室代碼 */
	@ApiModelProperty(notes = "科室代碼")
	private String unitId;

	/** 科室名稱 */
	@ApiModelProperty(notes = "科室名稱")
	private String unitName;

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
