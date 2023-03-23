package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import gov.kcg.kgo.model.KgoCasesetOfficer;
import gov.kcg.kgo.model.KgoOrgan;
import gov.kcg.kgo.model.KgoUnit;
import gov.kcg.kgo.model.KgoUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-受理機關設定-承辦人查詢")
@Entity
public class AcceptSetOfficerQueryDto {

	/** 承辦人員代碼 */
	@Id
	@ApiModelProperty(notes = "承辦人員代碼")
	@Column(name = "UserId")
	private String userId;

	/** 承辦人員姓名 */
	@ApiModelProperty(notes = "承辦人員姓名")
	@Column(name = "UserName")
	private String userName;

	/** 機關代碼 */
	@ApiModelProperty(notes = "機關代碼")
	@Column(name = "OrganId")
	private String organId;

	/** 機關名稱 */
	@ApiModelProperty(notes = "機關名稱")
	@Column(name = "OrganName")
	private String organName;

	/** 單位代碼 */
	@ApiModelProperty(notes = "單位代碼")
	@Column(name = "UnitId")
	private String unitId;

	/** 單位名稱 */
	@ApiModelProperty(notes = "單位名稱")
	@Column(name = "UnitName")
	private String unitName;

	public AcceptSetOfficerQueryDto(KgoUser us, KgoCasesetOfficer co, KgoOrgan o, KgoUnit un) {
		this.userId = co.getId().getCaseOfficer();
		this.userName = us.getName();
		this.organId = us.getOrgan();
		this.organName = o.getOrganName();
		this.unitId = us.getUnit();
		this.unitName = un.getUnitName();
	}

	public AcceptSetOfficerQueryDto(KgoUser us, KgoCasesetOfficer co, KgoOrgan o) {
		this.userId = co.getId().getCaseOfficer();
		this.userName = us.getName();
		this.organId = us.getOrgan();
		this.organName = o.getOrganName();
		this.unitId = us.getUnit();
		this.unitName = "";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

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
