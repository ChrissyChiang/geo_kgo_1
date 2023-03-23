package gov.kcg.kgo.viewModel.frontend.caseform.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModelProperty;

public class KcgMydataViewForm extends BaseViewForm {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "是否符合資格")
	private String socbuValue;

	public String getSocbuValue() {
		return socbuValue;
	}

	public void setSocbuValue(String socbuValue) {
		this.socbuValue = socbuValue;
	}

}
