package gov.kcg.kgo.viewModel.frontend.caseform.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.SaveActionViewForm;

public class SaveActionRs extends ApiBaseResponse<SaveActionViewForm> {
	private static final long serialVersionUID = 1L;

	public SaveActionRs(SaveActionViewForm viewForm) {
		super(viewForm);
	}
}