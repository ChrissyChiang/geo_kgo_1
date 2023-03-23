package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "服務案件清單-案件維護-初始畫面-案件管理者資料")
@Entity
public class CasesetManagerQueryByCaseSetIdDto {

	/** 機關ID */
	@Id
	@ApiModelProperty(notes = "機關ID")
	@Column(name = "ORGAN_ID")
	private String organId;

	/** 使用者名稱 */
	@ApiModelProperty(notes = "使用者名稱")
	@Column(name = "NAME")
	private String name;

	/** 使用者帳號 */
	@ApiModelProperty(notes = "使用者帳號")
	@Column(name = "USER_ID")
	private String userId;

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
