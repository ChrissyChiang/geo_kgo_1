package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import gov.kcg.kgo.model.KgoCasesetArea;
import gov.kcg.kgo.model.KgoOrgan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-受理機關設定-受理區機關查詢")
@Entity
public class AcceptSetAreaQueryDto {

	/** 機關代碼 */
	@Id
	@ApiModelProperty(notes = "機關代碼")
	@Column(name = "ORGAN")
	private String organ;

	/** 機關名稱 */
	@ApiModelProperty(notes = "機關名稱")
	@Column(name = "ORGAN_NAME")
	private String organName;

	/** 鄉鎮代碼 */
	@ApiModelProperty(notes = "鄉鎮代碼(選取多筆以,相串)")
	@Column(name = "ZIP")
	private String zip;

	public AcceptSetAreaQueryDto(KgoCasesetArea ca, KgoOrgan o) {
		this.organ = ca.getId().getOrgan();
		this.organName = o.getOrganName();
		this.zip = ca.getZip();
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
