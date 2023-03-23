package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchQueryViewForm;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchValidateViewForm;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "案件進度-查詢 rs")
public class CaseProcessSearchQueryRs extends ApiBaseResponse<CaseProcessSearchQueryViewForm> {
}