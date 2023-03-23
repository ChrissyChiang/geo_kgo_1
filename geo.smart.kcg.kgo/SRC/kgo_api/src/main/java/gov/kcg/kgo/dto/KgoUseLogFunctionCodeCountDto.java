package gov.kcg.kgo.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 *  使用者軌跡紀錄 - 功能使用分析.
 * 
 */
@Entity
public class KgoUseLogFunctionCodeCountDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FunctionCode", length=30)
	private String functionCode;
	
	@Column(name="Count")
	private Integer count;


	public KgoUseLogFunctionCodeCountDto() {
	}


	public String getFunctionCode() {
		return functionCode;
	}


	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}
}