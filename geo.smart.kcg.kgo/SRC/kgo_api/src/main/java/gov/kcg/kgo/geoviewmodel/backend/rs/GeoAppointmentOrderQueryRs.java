package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAppointmentOrderQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃-預約登錄管理:搜尋該預約服務已登錄預約單 rs
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:搜尋該預約服務已登錄預約單 rs")
public class GeoAppointmentOrderQueryRs extends ApiBaseResponse<GeoAppointmentOrderQueryViewForm> {
}

