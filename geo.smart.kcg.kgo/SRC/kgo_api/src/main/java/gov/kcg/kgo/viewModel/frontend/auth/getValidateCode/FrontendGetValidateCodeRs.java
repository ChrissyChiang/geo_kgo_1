package gov.kcg.kgo.viewModel.frontend.auth.getValidateCode;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.auth.getValidateCode.bean.FrontendGetValidateCodeViewForm;
import io.swagger.annotations.ApiModel;


/**
 * 前臺 取得驗證碼rs.
 */
@ApiModel(description = "前臺 取得驗證碼 rs")
public class FrontendGetValidateCodeRs extends ApiBaseResponse<FrontendGetValidateCodeViewForm>  {
	
	private static final long serialVersionUID = 1L;
}
