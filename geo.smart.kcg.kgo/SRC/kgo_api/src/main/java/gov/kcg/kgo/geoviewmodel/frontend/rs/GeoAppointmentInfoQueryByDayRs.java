package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoAppointmentInfoQueryByDayViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211015 add
 * 前台-線上預約臨櫃:取得單日預約資料 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "前台-線上預約臨櫃:取得單日預約資料 rs")
public class GeoAppointmentInfoQueryByDayRs extends ApiBaseResponse<GeoAppointmentInfoQueryByDayViewForm> {
}

