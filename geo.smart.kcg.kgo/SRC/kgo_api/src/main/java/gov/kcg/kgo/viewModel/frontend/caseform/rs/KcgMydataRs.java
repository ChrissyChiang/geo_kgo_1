package gov.kcg.kgo.viewModel.frontend.caseform.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.KcgMydataViewForm;

public class KcgMydataRs extends ApiBaseResponse<KcgMydataViewForm> {

	public KcgMydataRs(KcgMydataViewForm viewForm) {
		super(viewForm);
	}

	private static final long serialVersionUID = 1L;

}
