package gov.kcg.kgo.viewModel.backend.organUnitManagement.organQuery.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 機關科室管理-機關科室機關查詢 View Form
 */
@ApiModel(description = "機關科室管理-機關科室機關查詢 View Form")
public class OrganUnitManagementOrganQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關代碼")
	private String organId;

	@ApiModelProperty(value = "機關名稱")
	private String organName;

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

}
