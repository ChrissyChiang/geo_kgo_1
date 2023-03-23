package gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務申請-權限開通申請-額外設定欄位集合
 */
@ApiModel(description = "服務申請-權限開通申請-額外設定欄位集合")
public class ServiceApplyCasesetColumnDataGrid {

	/** 欄位名稱 */
	@ApiModelProperty(notes = "欄位名稱")
	private String columnName;

	/** 欄位型態 */
	@ApiModelProperty(notes = "欄位型態")
	private String columnType;

	/** 欄位ID */
	@ApiModelProperty(notes = "欄位ID")
	private String columnId;

	/** 版本號 */
	@ApiModelProperty(notes = "版本號")
	private Integer version;

	/** 欄位值 */
	@ApiModelProperty(notes = "欄位值")
	private String columnValue;

	/** 控制元件 */
	@ApiModelProperty(notes = "控制元件")
	private Object component;
	
	/** 是否必填 */
	@ApiModelProperty(notes = "是否必填")
	Boolean isMustKey;

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

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public Object getComponent() {
		return component;
	}

	public void setComponent(Object component) {
		this.component = component;
	}

	public Boolean getIsMustKey() {
		return isMustKey;
	}

	public void setIsMustKey(Boolean isMustKey) {
		this.isMustKey = isMustKey;
	}

}
