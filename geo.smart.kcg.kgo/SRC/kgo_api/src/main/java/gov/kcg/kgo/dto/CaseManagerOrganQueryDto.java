package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.model.KgoGroupLevel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台服務案件管理-機關分類")
@Entity
public class CaseManagerOrganQueryDto {

	/** 機關序號 */
	@ApiModelProperty(notes = "機關序號")
	@Id
	@Column(name = "OrganSeq")
	private String organSeq;

	/** 機關名稱 */
	@ApiModelProperty(notes = "機關名稱")
	@Column(name = "OrganName")
	private String organName;

	public CaseManagerOrganQueryDto() {

	}

	public CaseManagerOrganQueryDto(KgoGroupLevel groupLevel, KgoCaseset caseset) {
		this.organSeq = caseset.getOrgan();
		this.organName = groupLevel.getName();
	}

	public String getOrganSeq() {
		return organSeq;
	}

	public void setOrganSeq(String organSeq) {
		this.organSeq = organSeq;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}


}
