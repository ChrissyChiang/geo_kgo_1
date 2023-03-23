package gov.kcg.kgo.geoviewmodel.frontend.rs;

import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.CaseSetSearchDateViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

@ApiModel(value="線上場地租借-單月每日案件查詢 rs")
public class CaseSetSearchDateRs extends ApiBaseResponse<CaseSetSearchDateViewForm> {
    private static final long serialVersionUID = 1L;
}

