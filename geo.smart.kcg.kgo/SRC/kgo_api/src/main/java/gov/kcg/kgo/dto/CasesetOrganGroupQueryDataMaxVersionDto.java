package gov.kcg.kgo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *  GEO 20211109 add 機關審核表單
 * 	網路申辦-表單設定-群組維護-機關審核表單群組資料查詢集合
 */
@ApiModel(description = "網路申辦-表單設定-群組維護-機關審核表單群組資料查詢集合")
@Entity
public class CasesetOrganGroupQueryDataMaxVersionDto {

	@Id
	@ApiModelProperty(notes = "群組序號")
	@Column(name = "GROUP_SEQ")
	private Integer groupSeq;

	@ApiModelProperty(notes = "區塊說明")
	@Column(name = "MEMO")
	private String memo;

	@ApiModelProperty(notes = "版號")
	@Column(name = "VERSION")
	private Integer organFormVersion;

	@ApiModelProperty(notes = "顯示順序")
	@Column(name = "ORDER_NUM")
	private Integer orderNum;

	@ApiModelProperty(notes = "是否顯示機關審核表單")
	@Column(name = "IsShow")
	private Integer isShow;

	@ApiModelProperty(notes = "一般表單版本號")
	@Column(name = "CASE_FORM_VERSION")
	private Integer caseFormVersion;

	public CasesetOrganGroupQueryDataMaxVersionDto() {
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

	public Integer getOrganFormVersion() {
		return organFormVersion;
	}

	public void setOrganFormVersion(Integer version) {
		this.organFormVersion = version;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isSHow) {
		this.isShow = isSHow;
	}

	public Integer getCaseFormVersion() {
		return caseFormVersion;
	}

	public void setCaseFormVersion(Integer caseFormVersion) {
		this.caseFormVersion = caseFormVersion;
	}
}
