package gov.kcg.kgo.geoviewmodel.backend.rs;

import gov.kcg.kgo.geoviewmodel.backend.rs.bean.CancelAppointmentViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * 取消始 rs
 */

@ApiModel(description = "取消預約 rs")
public class CancelAppointmentRs extends ApiBaseResponse<CancelAppointmentViewForm> {
	private static final long serialVersionUID = 1L;
}
