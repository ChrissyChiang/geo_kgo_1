package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCaseProcessDetailViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20220828 add for 智能客服
 * 案件進度查詢
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "案件進度查詢 rs")
public class GeoCaseProcessDetailRs  extends ApiBaseResponse<GeoCaseProcessDetailViewForm> {
}
