package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAppointmentOrderFormQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * 後台-線上預約臨櫃-預約登錄管理:新增/編輯取得預約對應表單 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:新增/編輯取得預約對應表單 rs")
public class GeoAppointmentOrderFormQueryRs extends ApiBaseResponse<GeoAppointmentOrderFormQueryViewForm> {
}
