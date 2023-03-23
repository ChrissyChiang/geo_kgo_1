package gov.kcg.kgo.geoviewmodel.backend.rs;

import gov.kcg.kgo.geoviewmodel.backend.rs.bean.ApplyFormInitViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * 表單申請初始 rs
 */

@ApiModel(description = "申請表單儲存 rs")
public class ApplyFormInitRs extends ApiBaseResponse<ApplyFormInitViewForm> {
	private static final long serialVersionUID = 1L;
}
