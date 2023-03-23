package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoCrossOrganResultViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20221110 add
 * <p>
 * 跨機關callback rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "跨機關callback rs")
public class GeoCrossOrganResultRs extends ApiBaseResponse<GeoCrossOrganResultViewForm> {

}
