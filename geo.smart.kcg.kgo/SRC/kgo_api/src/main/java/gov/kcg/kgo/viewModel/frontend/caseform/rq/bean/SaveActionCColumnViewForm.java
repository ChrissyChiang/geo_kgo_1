package gov.kcg.kgo.viewModel.frontend.caseform.rq.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveActionCColumnViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "欄位ID")
	private String columnId;

	@ApiModelProperty(notes = "欄位值")
	private String value;

	@ApiModelProperty(notes = "欄位型態")
	private String columnType;

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

}
