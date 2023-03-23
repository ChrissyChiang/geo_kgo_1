package gov.kcg.kgo.geoviewmodel.frontend.rs;


import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.HomeActionViewForm;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20220711 add for 智能客服
 * 提供智能客服對應表單欄位
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "提供智能客服對應表單欄位 rs")
public class GeoGetCaseSetFormActionRs extends ApiBaseResponse<HomeActionViewForm> {

    public GeoGetCaseSetFormActionRs() {}

    public GeoGetCaseSetFormActionRs(HomeActionViewForm viewForm) {
        super(viewForm);
    }

    private static final long serialVersionUID = 1L;
}
