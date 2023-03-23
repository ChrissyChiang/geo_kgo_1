package gov.kcg.kgo.viewModel.commonApi.getstatus.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.commonApi.common.rs.bean.CaseColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用型查詢欄位作業 Form
 */
@ApiModel(description = "查詢案件狀態  ViewForm")
public class GetCaseStatusActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "案件狀態,  P：處理中 ; F：結案 ;  W：待處理 ; C：待補正 ; O：其他")
	private String stauts;
	
	@ApiModelProperty(value = "狀態說明 (status=O時，此欄有值)")
	private String statusDesc;

	public String getStauts() {
		return stauts;
	}

	public void setStauts(String stauts) {
		this.stauts = stauts;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}


}
