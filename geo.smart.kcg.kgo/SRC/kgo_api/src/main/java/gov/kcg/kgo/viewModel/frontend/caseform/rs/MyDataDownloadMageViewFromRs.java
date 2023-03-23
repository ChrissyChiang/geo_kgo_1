package gov.kcg.kgo.viewModel.frontend.caseform.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.MyDataDownloadMageViewFrom;

public class MyDataDownloadMageViewFromRs extends ApiBaseResponse<MyDataDownloadMageViewFrom> {

	private static final long serialVersionUID = 1L;

	public MyDataDownloadMageViewFromRs() {
		super();
	}

	public MyDataDownloadMageViewFromRs(MyDataDownloadMageViewFrom viewForm) {
		super(viewForm);
	}

}
