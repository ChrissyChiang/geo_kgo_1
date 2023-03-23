package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAppointmentOrderDateQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃-預約登錄管理:取得該服務預約時段 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:取得該服務預約時段 rs")
public class GeoAppointmentOrderDateQueryRs extends ApiBaseResponse<GeoAppointmentOrderDateQueryViewForm> {
}

