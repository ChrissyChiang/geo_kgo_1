package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchDetailViewForm;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchQueryViewForm;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "案件進度-明細 rs")
public class CaseProcessSearchDetailRs extends ApiBaseResponse<CaseProcessSearchDetailViewForm> {
}