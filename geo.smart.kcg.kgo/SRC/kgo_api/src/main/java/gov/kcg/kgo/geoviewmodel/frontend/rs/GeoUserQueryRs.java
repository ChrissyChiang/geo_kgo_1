package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoUserQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20221014 add_Jim
 * 查詢使用者資訊 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "查詢使用者資訊 rs")
public class GeoUserQueryRs extends ApiBaseResponse<GeoUserQueryViewForm> {
}

