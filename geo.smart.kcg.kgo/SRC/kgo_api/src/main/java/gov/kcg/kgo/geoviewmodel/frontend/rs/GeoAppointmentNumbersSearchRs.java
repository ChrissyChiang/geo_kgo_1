package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoAppointmentNumbersSearchViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單 rs")
public class GeoAppointmentNumbersSearchRs extends ApiBaseResponse<GeoAppointmentNumbersSearchViewForm> {
}

