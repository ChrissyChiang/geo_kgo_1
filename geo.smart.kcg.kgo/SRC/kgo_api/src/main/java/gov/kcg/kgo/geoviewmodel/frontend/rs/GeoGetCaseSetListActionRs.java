package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCaseSetListViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20220711 add for 智能客服
 * 提供智能客服B流程案件服務清單
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "提供智能客服B流程案件服務清單 rs")
public class GeoGetCaseSetListActionRs extends ApiBaseResponse<GeoCaseSetListViewForm> {
}
