package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAgentHomeViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211026 add
 * 後台-設定代理人:初始畫面 rs
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "後台-設定代理人:初始畫面 rs")
public class GeoAgentHomeRs extends ApiBaseResponse<GeoAgentHomeViewForm> {
}

