package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.ObjectUtils;

import gov.kcg.kgo.model.KgoCasesetUnit;
import gov.kcg.kgo.model.KgoOrgan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-受理機關設定-受理機關查詢")
@Entity
public class AcceptSetUnitQueryDto {

	/**
	 * 機關代碼
	 */
	@Id
	@ApiModelProperty(notes = "機關代碼")
	@Column(name = "Organ")
	private String organ;

	/**
	 * 機關名稱
	 */
	@ApiModelProperty(notes = "機關名稱")
	@Column(name = "OrganName")
	private String organName;

	public AcceptSetUnitQueryDto(KgoCasesetUnit u, KgoOrgan o) {
		this.organ = u.getId().getOrgan();
		if (ObjectUtils.isNotEmpty(o)) {
			this.organName = o.getOrganName();
		} else {
			this.organName = this.organ;
		}

	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

}
