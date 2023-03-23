package gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211109 add 機關審核表單
 * 網路申辦-表單設定- 機關審核表單 欄位群組維護-進版儲存-欄位資料
 */
@ApiModel(description = "網路申辦-表單設定- 機關審核表單欄位群組維護-進版儲存-欄位資料")
public class CasesetOrganColumnData {

	@ApiModelProperty(notes = "欄位ID",required = true)
	private String columnId;

	@ApiModelProperty(notes = "欄位名稱",required = true)
	private String columnName;

	@ApiModelProperty(notes = "欄位型態",required = true)
	private String columnType;

	@ApiModelProperty(notes = "欄位設定值")
	private String columnValue;

	@ApiModelProperty(notes = "顯示順序",required = true)
	private Integer orderNum;

	@ApiModelProperty(notes = "欄位長度",required = true)
	private Integer length;

	@ApiModelProperty(notes = "是否必填",required = true)
	private String isMustKey;

	@ApiModelProperty(notes = "備註說明")
	private String memo;

	@ApiModelProperty(notes = "附件類型")
	private String fileType;

	@ApiModelProperty(notes = "複合欄位資料集合",required = true)
	private List<List<CasesetOrganComplexColumnData>> complex;

	@ApiModelProperty(notes = "是否重複檢核",hidden = true)
	private Integer isCheckFrequency;

	@ApiModelProperty(notes = "是否欄位勾選",hidden = true)
	private Integer isFieldCheck;

	@ApiModelProperty(notes = "一般表單版本號", required = true)
	private Integer caseFormVersion;

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
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

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public List<List<CasesetOrganComplexColumnData>> getComplex() {
		return complex;
	}

	public void setComplex(List<List<CasesetOrganComplexColumnData>> complex) {
		this.complex = complex;
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
