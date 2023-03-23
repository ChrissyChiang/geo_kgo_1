package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCityCoinMembershipViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20221107 add_Jim
 * 查詢市民科技會員資訊 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "查詢市民科技會員資訊 rs")
public class GeoCityCoinMembershipRs extends ApiBaseResponse<GeoCityCoinMembershipViewForm> {
}

