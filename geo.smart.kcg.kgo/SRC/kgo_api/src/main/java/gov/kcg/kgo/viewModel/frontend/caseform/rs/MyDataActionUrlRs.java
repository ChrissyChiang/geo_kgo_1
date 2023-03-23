package gov.kcg.kgo.viewModel.frontend.caseform.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.MyDataActionUrlViewForm;

public class MyDataActionUrlRs extends ApiBaseResponse<MyDataActionUrlViewForm> {

	private static final long serialVersionUID = 1L;

	public MyDataActionUrlRs() {
		super();
	}

	public MyDataActionUrlRs(MyDataActionUrlViewForm viewForm) {
		super(viewForm);
	}
}
