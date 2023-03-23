package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAppointmentHomeViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃:初始畫面 rs
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "後台-線上預約臨櫃:初始畫面 rs")
public class GeoAppointmentHomeRs extends ApiBaseResponse<GeoAppointmentHomeViewForm> {
}

