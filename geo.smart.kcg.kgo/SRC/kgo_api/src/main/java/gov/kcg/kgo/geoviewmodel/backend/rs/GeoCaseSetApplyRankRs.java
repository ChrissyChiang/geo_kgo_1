package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoCaseSetApplyRankViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20210928 add
 * 服務申辦統計-取得申辦服務名次列表 rs
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "服務申辦統計-取得申辦服務名次列表 rs")
public class GeoCaseSetApplyRankRs extends ApiBaseResponse<GeoCaseSetApplyRankViewForm> {
}

