package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.SaveActionViewForm;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20220711 add for 智能客服
 * 提供智能客服服務案件入案存檔
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "提供智能客服服務案件入案存檔 rs")
public class GeoGetCaseSetSaveActionRs extends ApiBaseResponse<SaveActionViewForm> {

    private static final long serialVersionUID = 1L;

    public GeoGetCaseSetSaveActionRs(){

    }

    public GeoGetCaseSetSaveActionRs(SaveActionViewForm viewForm) {
        super(viewForm);
    }
}
