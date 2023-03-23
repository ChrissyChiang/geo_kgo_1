package gov.kcg.kgo.viewModel.commonApi.common.rs.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用型查詢欄位作業 CaseColumn
 */
@ApiModel(description = "通用型查詢欄位作業 CaseColumn")
public class CaseColumn implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "欄位ID", position=1)
	private String columnId;
	
	@ApiModelProperty(notes = "欄位名稱", position=2)
	private String columnName;

	public CaseColumn() {}

	/**
	 * @param columnName
	 * @param columnValue
	 */
	public CaseColumn(String columnId, String columnName) {
		this.columnId = columnId;
		this.columnName = columnName;
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
}
