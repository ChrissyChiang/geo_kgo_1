package gov.kcg.kgo.geoviewmodel.backend.rs;

import gov.kcg.kgo.geoviewmodel.backend.rs.bean.CaseSetQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * 服務查詢 rs
 */

@ApiModel(description = "服務查詢 rs")
public class CaseSetQueryRs extends ApiBaseResponse<CaseSetQueryViewForm> {
	private static final long serialVersionUID = 1L;
}
