package gov.kcg.kgo.viewModel.frontend.caseform.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.CheckOrganApplyViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.HomeActionViewForm;

public class CheckOrganApplyRs extends ApiBaseResponse<CheckOrganApplyViewForm> {

	private static final long serialVersionUID = 1L;

	public CheckOrganApplyRs() {}

	public CheckOrganApplyRs(CheckOrganApplyViewForm viewForm) {
		super(viewForm);
	}

}
