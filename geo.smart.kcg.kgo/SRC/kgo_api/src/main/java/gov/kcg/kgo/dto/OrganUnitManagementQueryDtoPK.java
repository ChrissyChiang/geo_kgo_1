package gov.kcg.kgo.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Embeddable
@ApiModel(description = "機關科室管理-機關科室查詢資料主Key")
public class OrganUnitManagementQueryDtoPK implements Serializable {

	private static final long serialVersionUID = 1L;

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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OrganUnitManagementQueryDtoPK)) {
			return false;
		}
		OrganUnitManagementQueryDtoPK castOther = (OrganUnitManagementQueryDtoPK) other;
		return this.organId.equals(castOther.organId) && this.unitId.equals(castOther.unitId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.organId.hashCode();
		hash = hash * prime + this.unitId.hashCode();

		return hash;
	}

}
