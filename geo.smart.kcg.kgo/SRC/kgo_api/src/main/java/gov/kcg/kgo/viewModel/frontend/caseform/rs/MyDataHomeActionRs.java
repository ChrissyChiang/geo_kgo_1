package gov.kcg.kgo.viewModel.frontend.caseform.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.MyDataHomeActionViewForm;

public class MyDataHomeActionRs extends ApiBaseResponse<MyDataHomeActionViewForm> {

	private static final long serialVersionUID = 1L;

	public MyDataHomeActionRs() {
		super();
	}

	public MyDataHomeActionRs(MyDataHomeActionViewForm viewForm) {
		super(viewForm);
	}

}
