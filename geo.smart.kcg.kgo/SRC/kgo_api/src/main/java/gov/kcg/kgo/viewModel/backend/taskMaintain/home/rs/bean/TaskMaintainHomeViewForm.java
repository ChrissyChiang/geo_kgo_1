package gov.kcg.kgo.viewModel.backend.taskMaintain.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.taskMaintain.query.rs.bean.TaskMaintainQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任務及公告管理-初始畫面 View Form
 */
@ApiModel(description = "任務及公告管理-初始畫面 View Form")
public class TaskMaintainHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 角色權限清單 @KgoRoleEnum */
	private List<String> roles;

	/** 發布對象下拉式選單 **/
	@ApiModelProperty(value = "發布對象下拉式選單")
	ComboBox releaseObejctComboBox;

	/** 任務搜尋結果清單 **/
	@ApiModelProperty(value = "任務搜尋結果清單")
	private List<TaskMaintainQueryDataGrid> grid;

	public ComboBox getReleaseObejctComboBox() {
		return releaseObejctComboBox;
	}

	public void setReleaseObejctComboBox(ComboBox releaseObejctComboBox) {
		this.releaseObejctComboBox = releaseObejctComboBox;
	}

	public List<TaskMaintainQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<TaskMaintainQueryDataGrid> grid) {
		this.grid = grid;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
