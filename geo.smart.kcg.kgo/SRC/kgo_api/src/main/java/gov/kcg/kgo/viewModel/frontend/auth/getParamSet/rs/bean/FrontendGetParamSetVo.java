package gov.kcg.kgo.viewModel.frontend.auth.getParamSet.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "前臺 參數設定資料")
public class FrontendGetParamSetVo extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	/** 參數名稱 */
	@ApiModelProperty(notes = "參數名稱", position = 0)
	private String type;

	/** 參數值 */
	@ApiModelProperty(notes = "參數值", position = 1)
	private String value;

	public FrontendGetParamSetVo() {}

	public FrontendGetParamSetVo(String type, String value) {
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
