package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoBidInstructionLightViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20210814 add
 *
 * 依行政區取得路燈資料 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "依行政區取得路燈資料 rs")
public class GeoBidInstructionLightRs extends ApiBaseResponse<GeoBidInstructionLightViewForm> {
}
