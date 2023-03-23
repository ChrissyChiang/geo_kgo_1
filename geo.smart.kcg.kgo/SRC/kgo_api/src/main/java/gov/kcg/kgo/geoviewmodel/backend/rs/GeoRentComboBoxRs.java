package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAgentQueryViewForm;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoRentComboBoxViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "後台-場地/活動線上預約預設")
public class GeoRentComboBoxRs  extends ApiBaseResponse<GeoRentComboBoxViewForm> {
}

