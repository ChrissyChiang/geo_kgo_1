package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetComplexColumnData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "後台-線上預約臨櫃-編輯：取得該預約對應表單-群組維護-欄位資料集合")
public class GeoAppointmentFormQueryGroupColumnDataModel {

	@ApiModelProperty(notes = "對應欄位")
	private String columnId;

	@ApiModelProperty(notes = "欄位名稱")
	private String columnName;

	@ApiModelProperty(notes = "欄位型態")
	private String columnType;

	@ApiModelProperty(notes = "欄位型態名稱")
	private String columnTypeName;

	@ApiModelProperty(notes = "欄位長度")
	private Integer length;

	@ApiModelProperty(notes = "必填")
	private String isMustKey;

	@ApiModelProperty(notes = "必填名稱")
	private String isMustKeyStr;

	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	@ApiModelProperty(notes = "欄位設定值")
	private String columnValue;

	@ApiModelProperty(value = "欄位備註")
	private String memo;

	@ApiModelProperty(notes = "附件類型")
	private String fileType;

	@ApiModelProperty(value = "複合欄位資料集合")
	private List<List<CasesetComplexColumnData>> complex;

	@ApiModelProperty(notes = "是否重複檢核")
	private String isCheckFrequency;

	@ApiModelProperty(notes = "是否重複檢核名稱")
	private String isCheckFrequencyStr;

	@ApiModelProperty(notes = "是否欄位勾選")
	private String isFieldCheck;

	@ApiModelProperty(notes = "是否欄位勾選名稱")
	private String isFieldCheckStr;

	public GeoAppointmentFormQueryGroupColumnDataModel() {
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getIsMustKey() {
		return isMustKey;
	}

	public void setIsMustKey(String isMustKey) {
		this.isMustKey = isMustKey;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	public String getIsMustKeyStr() {
		return isMustKeyStr;
	}

	public void setIsMustKeyStr(String isMustKeyStr) {
		this.isMustKeyStr = isMustKeyStr;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public List<List<CasesetComplexColumnData>> getComplex() {
		return complex;
	}

	public void setComplex(List<List<CasesetComplexColumnData>> complex) {
		this.complex = complex;
	}

	public String getIsCheckFrequency() {
		return isCheckFrequency;
	}

	public void setIsCheckFrequency(String isCheckFrequency) {
		this.isCheckFrequency = isCheckFrequency;
	}

	public String getIsCheckFrequencyStr() {
		return isCheckFrequencyStr;
	}

	public void setIsCheckFrequencyStr(String isCheckFrequencyStr) {
		this.isCheckFrequencyStr = isCheckFrequencyStr;
	}

	public String getIsFieldCheck() {
		return isFieldCheck;
	}

	public void setIsFieldCheck(String isFieldCheck) {
		this.isFieldCheck = isFieldCheck;
	}

	public String getIsFieldCheckStr() {
		return isFieldCheckStr;
	}

	public void setIsFieldCheckStr(String isFieldCheckStr) {
		this.isFieldCheckStr = isFieldCheckStr;
	}

}
