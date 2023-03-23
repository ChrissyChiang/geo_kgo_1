package gov.kcg.kgo.viewModel.frontend.auth.getParamSet.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.auth.getParamSet.rs.bean.FrontendGetParamSetViewForm;
import io.swagger.annotations.ApiModel;


/**
 * ApiResponse 共用object.
 */
@ApiModel(description = "前臺 參數設定資料 Rs")
public class FrontendGetParamSetRs extends ApiBaseResponse<FrontendGetParamSetViewForm>  {
	
	private static final long serialVersionUID = 1L;
}
