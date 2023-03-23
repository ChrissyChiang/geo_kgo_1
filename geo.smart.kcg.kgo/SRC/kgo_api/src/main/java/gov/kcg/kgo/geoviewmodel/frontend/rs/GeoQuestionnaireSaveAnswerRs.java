package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoQuestionnaireSaveAnswerViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20210923 add
 *
 * 問卷:儲存服務問卷答案(主檔、內容) rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "問卷:儲存服務問卷答案(主檔、內容) rs")
public class GeoQuestionnaireSaveAnswerRs extends ApiBaseResponse<GeoQuestionnaireSaveAnswerViewForm> {

}
