package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchDetailCheckViewForm;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchDetailViewForm;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "案件進度-檢核 rs")
public class CaseProcessSearchDetailCheckRs extends ApiBaseResponse<CaseProcessSearchDetailCheckViewForm> {
}