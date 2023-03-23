package gov.kcg.kgo.viewModel.frontend.caseform.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "前臺 案件表單申請-檢核")
public class ValidationActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "案件種類ID")
	private String caseSetId;

	@ApiModelProperty(notes = "分處")
	private String f3Name;

	@ApiModelProperty(notes = "錯誤訊息")
	private List<ValidationViewForm> validationMsg;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getF3Name() {
		return f3Name;
	}

	public void setF3Name(String f3Name) {
		this.f3Name = f3Name;
	}

	public List<ValidationViewForm> getValidationMsg() {
		return validationMsg;
	}

	public void setValidationMsg(List<ValidationViewForm> validationMsg) {
		this.validationMsg = validationMsg;
	}

}
