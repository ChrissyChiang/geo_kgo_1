package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCaseSetApplyRankDetailListViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoQuestionnaireCaseSetQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20210814 add
 *
 * 前台-服務申辦統計: 取得申辦服務名次列表 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "前台-服務申辦統計: 取得申辦服務名次列表 rs")
public class GeoCaseSetApplyRankDetailListRs extends ApiBaseResponse<GeoCaseSetApplyRankDetailListViewForm> {

}
