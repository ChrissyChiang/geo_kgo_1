package gov.kcg.kgo.viewModel.frontend.caseform.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetComplexColumnData;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModelProperty;

public class ColumnViewForm extends BaseViewForm {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "案件ID")
	private String caseSetId = "";

	@ApiModelProperty(notes = "群組序號")
	private Integer groupSeq;

	@ApiModelProperty(notes = "版本號")
	private Integer version;

	@ApiModelProperty(notes = "欄位ID")
	private String columnId = "";

	@ApiModelProperty(notes = "欄位名稱")
	private String columnName = "";

	@ApiModelProperty(notes = "欄位型態")
	private String columnType = "";

	/** 欄位型態名稱 */
	@ApiModelProperty(notes = "欄位型態名稱")
	private String columnTypeName = "";

	@ApiModelProperty(notes = "欄位長度")
	private Integer length;

	@ApiModelProperty(notes = "是否必填")
	private Boolean isMustKey;

	@ApiModelProperty(notes = "欄位設定值")
	private String columnValue = "";

	@ApiModelProperty(notes = "欄位值")
	private String value = "";

	@ApiModelProperty(notes = "是否唯讀")
	private Boolean isReadonly;

	@ApiModelProperty(notes = "驗證訊息")
	private String validationMsg = "";

	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	@ApiModelProperty(value = "欄位備註")
	private String memo = "";

	@ApiModelProperty(value = "市府MyData服務")
	private String cityApiServiceId;

	@ApiModelProperty(value = "附件,上傳檔案限制副檔名")
	private String fileType;

	@ApiModelProperty(value = "複合欄位資料集合")
	private List<List<CasesetComplexColumnData>> complex;

	/**
	 * GEO 20211019 add
	 */
	@ApiModelProperty(notes = "是否重複檢核")
	private String isCheckFrequency;

	public ColumnViewForm(){
		super();
	}

	public ColumnViewForm(String columnId, String columnName, String columnType, String columnValue, Boolean isReadonly) {
		this.columnId = columnId;
		this.columnName = columnName;
		this.columnType = columnType;
		this.columnValue = columnValue;
		this.isReadonly = isReadonly;
	}

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

	public Boolean getIsMustKey() {
		return isMustKey;
	}

	public void setIsMustKey(Boolean isMustKey) {
		this.isMustKey = isMustKey;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public Boolean getIsReadonly() {
		return isReadonly;
	}

	public void setIsReadonly(Boolean isReadonly) {
		this.isReadonly = isReadonly;
	}

	public String getValidationMsg() {
		return validationMsg;
	}

	public void setValidationMsg(String validationMsg) {
		this.validationMsg = validationMsg;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<List<CasesetComplexColumnData>> getComplex() {
		return complex;
	}

	public void setComplex(List<List<CasesetComplexColumnData>> complex) {
		this.complex = complex;
	}

	public String getCityApiServiceId() {
		return cityApiServiceId;
	}

	public void setCityApiServiceId(String cityApiServiceId) {
		this.cityApiServiceId = cityApiServiceId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getIsCheckFrequency() {
		return isCheckFrequency;
	}

	public void setIsCheckFrequency(String isCheckFrequency) {
		this.isCheckFrequency = isCheckFrequency;
	}
}
