package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoQuestionnaireCaseSetQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20210814 add
 *
 * 取得服務問卷主內容 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "取得服務問卷主內容 rs")
public class GeoQuestionnaireCaseSetQueryRs extends ApiBaseResponse<GeoQuestionnaireCaseSetQueryViewForm> {

}
