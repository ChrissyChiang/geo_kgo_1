package gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211109 add 機關審核表單
 * 網路申辦-表單設定- 機關審核表單 複合欄位資料集合
 */
@ApiModel(description = "網路申辦-表單設定- 機關審核表單 複合欄位資料集合")
public class CasesetOrganComplexColumnData {

	@ApiModelProperty(notes = "複合欄位ID")
	private String cColumnId = "";

	@ApiModelProperty(notes = "欄位型態")
	private String columnType = "";

	@ApiModelProperty(notes = "欄位設定值")
	private String columnValue = "";

	@ApiModelProperty(notes = "欄位長度")
	private Integer length;

	@ApiModelProperty(notes = "是否必填")
	private String isMustKey = "";

	@ApiModelProperty(notes = "父類別欄位ID")
	private String pColumnId = "";

	@ApiModelProperty(notes = "前文字")
	private String fText = "";

	@ApiModelProperty(notes = "後文字")
	private String bText = "";

	@ApiModelProperty(notes = "群組編號")
	private String vGroup = "";

	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	@ApiModelProperty(notes = "所在行數")
	private Integer row;

	@ApiModelProperty(notes = "備註")
	private String memo = "";

	@ApiModelProperty(notes = "欄位值")
	private String value = "";

	@ApiModelProperty(notes = "是否重複檢核")
	private Integer isCheckFrequency;

	@ApiModelProperty(notes = "是否欄位勾選")
	private Integer isFieldCheck;

	@ApiModelProperty(notes = "一般表單版本號")
	private Integer caseFormVersion;

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

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getIsMustKey() {
		return isMustKey;
	}

	public void setIsMustKey(String isMustKey) {
		this.isMustKey = isMustKey;
	}

	public String getpColumnId() {
		return pColumnId;
	}

	public void setpColumnId(String pColumnId) {
		this.pColumnId = pColumnId;
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

	public String getvGroup() {
		return vGroup;
	}

	public void setvGroup(String vGroup) {
		this.vGroup = vGroup;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getIsCheckFrequency() {
		return isCheckFrequency;
	}

	public void setIsCheckFrequency(Integer isCheckFrequency) {
		this.isCheckFrequency = isCheckFrequency;
	}

	public Integer getIsFieldCheck() {
		return isFieldCheck;
	}

	public void setIsFieldCheck(Integer isFieldCheck) {
		this.isFieldCheck = isFieldCheck;
	}

	public Integer getCaseFormVersion() {
		return caseFormVersion;
	}

	public void setCaseFormVersion(Integer caseFormVersion) {
		this.caseFormVersion = caseFormVersion;
	}
}
