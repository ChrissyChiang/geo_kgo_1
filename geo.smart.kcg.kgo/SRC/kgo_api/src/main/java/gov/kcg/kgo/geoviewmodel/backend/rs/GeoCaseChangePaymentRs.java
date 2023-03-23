package gov.kcg.kgo.geoviewmodel.backend.rs;

import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAppointmentOrderQueryViewForm;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoCaseChangePaymentForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("後台案件管理 - 變更繳費狀態 rs")
public class GeoCaseChangePaymentRs extends ApiBaseResponse<GeoCaseChangePaymentForm>{
}
