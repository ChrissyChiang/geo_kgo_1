package gov.kcg.kgo.viewModel.backend.organUnitManagement.home.rs.bean;

import javax.persistence.Column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 機關科室管理-初始畫面-資料集合
 */
@ApiModel(description = "機關科室管理-初始畫面-資料集合")
public class OrganUnitManagementHomeDataGrid {

	/**
	 * 機關名稱
	 */
	@ApiModelProperty(notes = "機關名稱")
	@Column(name = "OrganName")
	private String organName;

	/**
	 * 單位名稱
	 */
	@ApiModelProperty(notes = "單位名稱")
	@Column(name = "UnitName")
	private String unitName;

	/**
	 * 機關代碼
	 */
	@ApiModelProperty(notes = "機關代碼")
	@Column(name = "OrganId")
	private String organId;

	/**
	 * 單位代碼
	 */
	@ApiModelProperty(notes = "單位代碼")
	@Column(name = "UnitId")
	private String unitId;

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

}
