package gov.kcg.kgo.geoviewmodel.backend.rs;

import gov.kcg.kgo.geoviewmodel.backend.rs.bean.CaseSetRentTimeQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * 後台場地租借-時段查詢
 */

@ApiModel(description = "後台場地租借-時段查詢 rs")
public class CaseSetSiteTimeQueryRs extends ApiBaseResponse<CaseSetRentTimeQueryViewForm> {
	private static final long serialVersionUID = 1L;
}
