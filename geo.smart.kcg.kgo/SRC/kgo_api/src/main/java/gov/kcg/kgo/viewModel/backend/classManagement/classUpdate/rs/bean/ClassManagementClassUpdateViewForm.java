package gov.kcg.kgo.viewModel.backend.classManagement.classUpdate.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分類維護功能-類別新增/維護 View Form
 */
@ApiModel(description = "分類維護功能-類別新增/維護 View Form")
public class ClassManagementClassUpdateViewForm extends BaseViewForm {

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
