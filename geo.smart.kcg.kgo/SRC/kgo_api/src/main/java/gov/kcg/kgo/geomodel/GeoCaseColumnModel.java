package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(description = "後台案件處理-案件欄位資料集合")
public class GeoCaseColumnModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "案件ID")
	private String caseId;

	@ApiModelProperty(notes = "欄位ID")
	private String columnId;

	@ApiModelProperty(notes = "欄位名稱")
	private String columnName;

	@ApiModelProperty(notes = "欄位值")
	private String columnValue;

	@ApiModelProperty(notes = "欄位型態")
	private String ColumnType;


	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
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

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public String getColumnType() {
		return ColumnType;
	}

	public void setColumnType(String columnType) {
		ColumnType = columnType;
	}
}
