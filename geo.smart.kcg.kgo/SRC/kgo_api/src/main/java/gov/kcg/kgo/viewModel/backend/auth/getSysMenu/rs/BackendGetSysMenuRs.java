package gov.kcg.kgo.viewModel.backend.auth.getSysMenu.rs;

import gov.kcg.kgo.viewModel.backend.auth.getSysMenu.rs.bean.BackendGetSysMenuViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;


/**
 * 取得角色功能選單  rs.
 */
@ApiModel(description = "取得角色功能選單  rs")
public class BackendGetSysMenuRs extends ApiBaseResponse<BackendGetSysMenuViewForm>  {
	
	private static final long serialVersionUID = 1L;
}
