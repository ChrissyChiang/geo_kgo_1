package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.viewModel.backend.template.view.rs.TemplateViewComplexColumnData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211108 add for 機關審核表單
 * 後台表單維護–取得機關審核表單初始欄位明細
 */
@ApiModel(description = "後台表單維護–取得機關審核表單初始欄位明細-欄位資料集合")
public class GeoTemplateOrganViewColumnDataGrid {

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
	private List<List<TemplateViewComplexColumnData>> complex;

	public GeoTemplateOrganViewColumnDataGrid() {
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

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
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

	public String getIsMustKeyStr() {
		return isMustKeyStr;
	}

	public void setIsMustKeyStr(String isMustKeyStr) {
		this.isMustKeyStr = isMustKeyStr;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
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

	public List<List<TemplateViewComplexColumnData>> getComplex() {
		return complex;
	}

	public void setComplex(List<List<TemplateViewComplexColumnData>> complex) {
		this.complex = complex;
	}
}
