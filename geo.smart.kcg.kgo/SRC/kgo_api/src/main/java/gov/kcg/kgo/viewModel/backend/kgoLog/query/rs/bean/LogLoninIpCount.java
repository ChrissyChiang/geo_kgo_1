package gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import gov.kcg.kgo.dto.KgoUseLogIpCountDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 *  使用者軌跡紀錄 - 承辦登入IP.
 * 
 */
@ApiModel(description = "後台 - 軌跡紀錄 - 查詢 - 承辦登入IP")
public class LogLoninIpCount implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "IP")
	private String ip;
	
	@ApiModelProperty(value = "數量")
	private Integer count;


	public LogLoninIpCount() {
	}
	
	public LogLoninIpCount(KgoUseLogIpCountDto dto) {
		this.ip = dto.getIp();
		this.count = dto.getCount();
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