package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoMyDataModelViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20221020 add_Jim
 * MyData Model查詢 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "MyData Model查詢 rs")
public class GeoMyDataModelRs extends ApiBaseResponse<GeoMyDataModelViewForm> {
}

