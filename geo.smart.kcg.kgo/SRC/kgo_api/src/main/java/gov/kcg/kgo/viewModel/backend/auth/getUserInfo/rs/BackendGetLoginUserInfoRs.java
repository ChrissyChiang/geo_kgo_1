package gov.kcg.kgo.viewModel.backend.auth.getUserInfo.rs;

import gov.kcg.kgo.viewModel.backend.auth.getUserInfo.rs.bean.BackendGetLoginUserInfoViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;


/**
 * 取得使用者資訊  rs.
 */
@ApiModel(description = "取得使用者資訊  rs")
public class BackendGetLoginUserInfoRs extends ApiBaseResponse<BackendGetLoginUserInfoViewForm>  {
	
	private static final long serialVersionUID = 1L;
}
