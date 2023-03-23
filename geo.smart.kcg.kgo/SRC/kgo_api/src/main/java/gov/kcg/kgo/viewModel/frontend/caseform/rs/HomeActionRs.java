package gov.kcg.kgo.viewModel.frontend.caseform.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.HomeActionViewForm;

public class HomeActionRs extends ApiBaseResponse<HomeActionViewForm> {

	public HomeActionRs() {}
	
	public HomeActionRs(HomeActionViewForm viewForm) {
		super(viewForm);
	}

	private static final long serialVersionUID = 1L;

}
