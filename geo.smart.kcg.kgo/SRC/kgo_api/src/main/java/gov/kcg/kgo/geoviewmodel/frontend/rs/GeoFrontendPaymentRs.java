package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geomodel.ktcpay.GeoKgoPaymentModel;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "前台繳費 rs")
public class GeoFrontendPaymentRs extends ApiBaseResponse<GeoKgoPaymentModel> {
}

