package gov.kcg.kgo.viewModel.backend.organUnitManagement.editHome.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "機關科室管理-機關科室維護(新增/修改)初始畫面 rq")
public class OrganUnitManagementEditHomeRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關代碼")
	private String organId;

	@ApiModelProperty(value = "科室代碼")
	private String unitId;

	/**
	 * 2選1
	 * organ = 機關
	 * unit = 單位
	 */
	@ApiModelProperty(value = "類型代碼")
	private String type;

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
