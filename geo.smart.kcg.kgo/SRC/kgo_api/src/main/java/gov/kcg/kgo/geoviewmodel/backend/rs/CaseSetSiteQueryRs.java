package gov.kcg.kgo.geoviewmodel.backend.rs;

import gov.kcg.kgo.geoviewmodel.backend.rs.bean.CaseSetSiteQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * 服務場地查詢 rs  Roy
 */

@ApiModel(description = "服務場地查詢 rs")
public class CaseSetSiteQueryRs extends ApiBaseResponse<CaseSetSiteQueryViewForm> {
	private static final long serialVersionUID = 1L;
}
