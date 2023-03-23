package gov.kcg.kgo.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import gov.kcg.kgo.model.KgoUseLog;


/**
 * The persistent class for the KGO_USE_LOG database table.
 * 
 */
@Entity
public class KgoUseLogLoginTypeCountDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LoginType", length=30)
	private String loginType;
	
	@Column(name="Count")
	private Integer count;


	public KgoUseLogLoginTypeCountDto() {
	}
	
	public KgoUseLogLoginTypeCountDto(String loginType, Long count) {
		this.loginType = loginType;
		this.count = count == null ? 0 : count.intValue();
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}