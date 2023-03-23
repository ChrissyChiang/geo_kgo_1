package gov.kcg.kgo.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 *  使用者軌跡紀錄 - 承辦登入IP.
 * 
 */
@Entity
public class KgoUseLogIpCountDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IP", length=30)
	private String ip;
	
	@Column(name="Count")
	private Integer count;


	public KgoUseLogIpCountDto() {
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}
}