package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoQuestionnaireResultQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211012 add
 * 後台-問卷結果查詢:問卷結果(配分) rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "取得後台-問卷結果查詢:問卷結果(配分) rs")
public class GeoQuestionnaireResultScoreQueryRs extends ApiBaseResponse<GeoQuestionnaireResultQueryViewForm> {

}
