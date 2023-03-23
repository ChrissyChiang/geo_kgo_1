package gov.kcg.kgo.viewModel.backend.auth.getSysMenu.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色功能選單.
 */
@ApiModel(description = "角色功能選單  ViewForm")
public class BackendGetSysMenuViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "角色功能選單")
	/** 角色功能選單. */
	private List<SysMenu> menuList;

	public List<SysMenu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<SysMenu> menuList) {
		this.menuList = menuList;
	}
}
