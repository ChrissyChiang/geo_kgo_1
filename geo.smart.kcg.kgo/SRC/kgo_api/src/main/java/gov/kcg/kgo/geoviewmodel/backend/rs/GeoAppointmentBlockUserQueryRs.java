package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAppointmentBlockUserQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211104 add
 * 後台-線上預約臨櫃:查詢黑名單清單 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "後台-線上預約臨櫃:查詢黑名單清單 rs")
public class GeoAppointmentBlockUserQueryRs extends ApiBaseResponse<GeoAppointmentBlockUserQueryViewForm> {
}

