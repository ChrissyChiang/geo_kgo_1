package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoQuestionnaireTopicListViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20210828 add
 *
 * 問卷題組 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "問卷題組 rs")
public class GeoQuestionnaireTopicListRs extends ApiBaseResponse<GeoQuestionnaireTopicListViewForm> {

}
