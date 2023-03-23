package gov.kcg.kgo.viewModel.backend.auth.login.rs;

import gov.kcg.kgo.viewModel.backend.auth.login.rs.bean.BackendLoginAuthInfo;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;


/**
 * 登入後驗證資料.
 */
@ApiModel(description = "登入後驗證資料 rs")
public class BackendLoginRs extends ApiBaseResponse<BackendLoginAuthInfo>  {
	
	private static final long serialVersionUID = 1L;
}
