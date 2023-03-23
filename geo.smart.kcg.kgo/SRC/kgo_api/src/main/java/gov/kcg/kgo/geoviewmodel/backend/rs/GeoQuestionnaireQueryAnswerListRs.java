package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoQuestionnaireQueryAnswerViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211012 add
 * 後台-檢視填寫:取得答案清單 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "後台-檢視填寫:取得答案清單 rs")
public class GeoQuestionnaireQueryAnswerListRs extends ApiBaseResponse<GeoQuestionnaireQueryAnswerViewForm> {

}
