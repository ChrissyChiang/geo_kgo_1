package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoQuestionnaireTopicQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20210814 add
 *
 * 取得問卷題組主內容 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "取得問卷題組主內容 rs")
public class GeoQuestionnaireTopicQueryRs extends ApiBaseResponse<GeoQuestionnaireTopicQueryViewForm> {

}
