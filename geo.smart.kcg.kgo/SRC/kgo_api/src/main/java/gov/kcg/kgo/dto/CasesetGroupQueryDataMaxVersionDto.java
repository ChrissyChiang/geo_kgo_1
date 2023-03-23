package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-表單設定-群組維護-群組資料查詢集合")
@Entity
public class CasesetGroupQueryDataMaxVersionDto {

	/** 群組序號 */
	@Id
	@ApiModelProperty(notes = "群組序號")
	@Column(name = "GROUP_SEQ")
	private Integer groupSeq;

	/** 區塊說明 */
	@ApiModelProperty(notes = "區塊說明")
	@Column(name = "MEMO")
	private String memo;

	/** 版號 */
	@ApiModelProperty(notes = "版號")
	@Column(name = "VERSION")
	private Integer version;

	/** 顯示順序 */
	@ApiModelProperty(notes = "顯示順序")
	@Column(name = "ORDER_NUM")
	private Integer orderNum;

	/** GEO 20211203 add 重複檢核 */
	@ApiModelProperty(notes = "重複檢核時間")
	@Column(name = "CHECK_FREQUENCY_PERIOD")
	private String CheckFrequencyPeriod;

	public CasesetGroupQueryDataMaxVersionDto() {
	}

	public Integer getGroupSeq() {
		return groupSeq;
	}

	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getCheckFrequencyPeriod() {
		return CheckFrequencyPeriod;
	}

	public void setCheckFrequencyPeriod(String checkFrequencyPeriod) {
		CheckFrequencyPeriod = checkFrequencyPeriod;
	}
}
