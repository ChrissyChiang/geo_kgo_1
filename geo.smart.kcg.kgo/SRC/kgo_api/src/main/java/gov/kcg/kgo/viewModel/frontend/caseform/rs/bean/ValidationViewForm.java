package gov.kcg.kgo.viewModel.frontend.caseform.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModelProperty;

public class ValidationViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "欄位ID")
	private String columnId;

	@ApiModelProperty(notes = "欄位名稱")
	private String columnName;

	@ApiModelProperty(notes = "驗證訊息")
	private String validationMsg;

	public ValidationViewForm() {
		super();
	}

	public ValidationViewForm(String columnId, String columnName, String validationMsg) {
		this.columnId = columnId;
		this.columnName = columnName;
		this.validationMsg = validationMsg;
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

	public String getValidationMsg() {
		return validationMsg;
	}

	public void setValidationMsg(String validationMsg) {
		this.validationMsg = validationMsg;
	}
}
