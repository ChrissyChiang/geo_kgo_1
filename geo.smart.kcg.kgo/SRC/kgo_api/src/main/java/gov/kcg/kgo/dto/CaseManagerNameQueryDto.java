package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import gov.kcg.kgo.model.KgoCaseManager;
import gov.kcg.kgo.model.KgoUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件維護管理-管理者搜尋")
@Entity
public class CaseManagerNameQueryDto {

	/** 管理人員帳號 */
	@Id
	@ApiModelProperty(notes = "管理人員帳號")
	@Column(name = "MANAGER")
	private String manager;

	/** 使用者姓名 */
	@ApiModelProperty(notes = "使用者姓名")
	@Column(name = "NAME")
	private String name;

	public CaseManagerNameQueryDto() {
	}

	public CaseManagerNameQueryDto(KgoCaseManager cm, KgoUser u) {
		this.manager = u.getUserId();
		this.name = u.getName();
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
