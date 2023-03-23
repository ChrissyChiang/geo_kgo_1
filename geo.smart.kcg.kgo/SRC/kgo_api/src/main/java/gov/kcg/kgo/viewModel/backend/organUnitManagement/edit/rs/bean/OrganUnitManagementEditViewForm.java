package gov.kcg.kgo.viewModel.backend.organUnitManagement.edit.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 機關科室管理-機關科室維護(新增/修改) View Form
 */
@ApiModel(description = "機關科室管理-機關科室維護(新增/修改) View Form")
public class OrganUnitManagementEditViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 執行結果訊息 **/
	@ApiModelProperty(value = "執行結果訊息")
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
