package gov.kcg.kgo.viewModel.backend.serviceApply.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.serviceApply.query.rs.bean.ServiceApplyQueryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務申請-初始畫面View Form
 */
@ApiModel(description = "服務申請-初始畫面View Form")
public class ServiceApplyHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 臨櫃申請清單 **/
	@ApiModelProperty(value = "臨櫃申請清單")
	private List<ServiceApplyQueryDataGrid> grid;

	/** 案件類別下拉式選單 **/
	@ApiModelProperty(value = "案件類別下拉式選單")
	private ComboBox caseTypeComboBox;

	public List<ServiceApplyQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<ServiceApplyQueryDataGrid> grid) {
		this.grid = grid;
	}

	public ComboBox getCaseTypeComboBox() {
		return caseTypeComboBox;
	}

	public void setCaseTypeComboBox(ComboBox caseTypeComboBox) {
		this.caseTypeComboBox = caseTypeComboBox;
	}

}
