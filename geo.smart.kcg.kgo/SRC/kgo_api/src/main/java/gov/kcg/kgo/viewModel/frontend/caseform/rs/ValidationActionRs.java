package gov.kcg.kgo.viewModel.frontend.caseform.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.ValidationActionViewForm;

public class ValidationActionRs extends ApiBaseResponse<ValidationActionViewForm> {
	private static final long serialVersionUID = 1L;

	public ValidationActionRs() {
	}

	public ValidationActionRs(ValidationActionViewForm viewForm) {
		super(viewForm);
		// TODO Auto-generated constructor stub
	}

}
