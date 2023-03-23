package gov.kcg.kgo.geoviewmodel.backend.rs;

import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAppointmentBidInstructionHomeViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoAppointmentInfoQueryByDayViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;



/**
 20220811 GEO add
 * 前台-線上預約臨櫃:取得同意說明頁
 */
@ApiModel(description = "前台-線上預約臨櫃:取得同意說明頁 rs")
public class AppointmentBidInstructionHomeRs extends ApiBaseResponse<GeoAppointmentBidInstructionHomeViewForm> {
}
