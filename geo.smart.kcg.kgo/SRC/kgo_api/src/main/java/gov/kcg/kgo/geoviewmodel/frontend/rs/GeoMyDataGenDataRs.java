package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoMyDataGenDataViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20221020 add_Jim
 * MyData Gen Data rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "MyData Gen Data rs")
public class GeoMyDataGenDataRs extends ApiBaseResponse<GeoMyDataGenDataViewForm> {
}

