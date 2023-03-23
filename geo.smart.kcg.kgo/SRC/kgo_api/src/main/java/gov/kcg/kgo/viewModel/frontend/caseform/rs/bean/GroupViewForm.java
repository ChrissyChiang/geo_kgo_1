package gov.kcg.kgo.viewModel.frontend.caseform.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModelProperty;

public class GroupViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "案件種類ID")
	private String caseSetId;

	@ApiModelProperty(notes = "群組序號")
	private Integer groupSeq;

	@ApiModelProperty(notes = "版本號")
	private Integer version;

	@ApiModelProperty(notes = "區塊說明")
	private String memo;

	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	/**GEO 20211019 add */
	@ApiModelProperty(notes = "重複檢核區間 年-\"Year\",月-\"Month\",不檢核-null")
	private String checkFrequencyPeriod;

	@ApiModelProperty(notes = "案件欄位")
	private List<ColumnViewForm> columnData;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public Integer getGroupSeq() {
		return groupSeq;
	}

	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public List<ColumnViewForm> getColumnData() {
		return columnData;
	}

	public void setColumnData(List<ColumnViewForm> columnData) {
		this.columnData = columnData;
	}

	public String getCheckFrequencyPeriod() {
		return checkFrequencyPeriod;
	}

	public void setCheckFrequencyPeriod(String checkFrequencyPeriod) {
		this.checkFrequencyPeriod = checkFrequencyPeriod;
	}
}
