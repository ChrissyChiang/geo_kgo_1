package gov.kcg.kgo.viewModel.frontend.auth.getUserInfo.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.auth.getUserInfo.rs.bean.FrontendGetLoginUserInfoViewForm;
import io.swagger.annotations.ApiModel;


/**
 * 前台 - 取得使用者資訊  rs.
 */
@ApiModel(description = "前台 -取得使用者資訊  rs")
public class FrontendGetLoginUserInfoRs extends ApiBaseResponse<FrontendGetLoginUserInfoViewForm>  {
	
	private static final long serialVersionUID = 1L;
}
