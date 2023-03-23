package gov.kcg.kgo.viewModel.commonApi.notify.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查詢案件狀態 bean
 */
@ApiModel(description = "查詢案件狀態 bean")
public class QueryStatasInfo extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "案件狀態")
	private String stauts;
	
	@ApiModelProperty(value = "申請人員編號")
	private String applyUser;

	public String getStauts() {
		return stauts;
	}

	public void setStauts(String stauts) {
		this.stauts = stauts;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}
}
