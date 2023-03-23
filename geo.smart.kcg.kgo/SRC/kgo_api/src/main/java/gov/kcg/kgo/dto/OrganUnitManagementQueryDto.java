package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "機關科室管理-機關科室查詢")
@Entity
public class OrganUnitManagementQueryDto {

	@EmbeddedId
	private OrganUnitManagementQueryDtoPK id;

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

	public OrganUnitManagementQueryDtoPK getId() {
		return id;
	}

	public void setId(OrganUnitManagementQueryDtoPK id) {
		this.id = id;
	}

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

}
