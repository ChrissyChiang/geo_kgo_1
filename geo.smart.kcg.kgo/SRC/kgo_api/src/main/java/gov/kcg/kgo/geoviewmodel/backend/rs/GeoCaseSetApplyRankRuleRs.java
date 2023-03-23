package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoCaseSetApplyRankViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211123 add
 * 服務申辦統計-儲存統計頻率 rs
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "服務申辦統計-儲存統計頻率 rs")
public class GeoCaseSetApplyRankRuleRs extends ApiBaseResponse<GeoCaseSetApplyRankViewForm> {
}

