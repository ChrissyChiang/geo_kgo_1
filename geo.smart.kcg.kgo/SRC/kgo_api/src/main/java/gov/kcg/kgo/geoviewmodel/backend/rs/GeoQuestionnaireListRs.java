package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoQuestionnaireListViewForm;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20210828 add
 *
 * 問卷設定 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "問卷設定 rs")
public class GeoQuestionnaireListRs extends ApiBaseResponse<GeoQuestionnaireListViewForm> {

}
