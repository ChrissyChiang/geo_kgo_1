package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean;

import io.swagger.annotations.ApiModelProperty;

public class CaseHandleCaseViewSaCaseApplyDataDataGridComplex {

	@ApiModelProperty(notes = "複合欄位ID")
	private String cColumnId = "";

	@ApiModelProperty(notes = "欄位型態")
	private String columnType = "";

	@ApiModelProperty(notes = "欄位設定值")
	private String columnValue = "";

	@ApiModelProperty(notes = "前文字")
	private String fText = "";

	@ApiModelProperty(notes = "後文字")
	private String bText = "";

	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	@ApiModelProperty(notes = "所在行數")
	private Integer row;

	@ApiModelProperty(notes = "欄位值")
	private String value = "";

	/** 補正值 */
	@ApiModelProperty(notes = "補正值")
	private String correctBValue;

	public String getcColumnId() {
		return cColumnId;
	}

	public void setcColumnId(String cColumnId) {
		this.cColumnId = cColumnId;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public String getfText() {
		return fText;
	}

	public void setfText(String fText) {
		this.fText = fText;
	}

	public String getbText() {
		return bText;
	}

	public void setbText(String bText) {
		this.bText = bText;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCorrectBValue() {
		return correctBValue;
	}

	public void setCorrectBValue(String correctBValue) {
		this.correctBValue = correctBValue;
	}

}
