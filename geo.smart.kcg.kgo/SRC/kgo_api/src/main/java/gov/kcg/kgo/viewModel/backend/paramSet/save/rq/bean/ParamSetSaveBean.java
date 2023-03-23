package gov.kcg.kgo.viewModel.backend.paramSet.save.rq.bean;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台 - 參數設定 - 儲存 物件")
public class ParamSetSaveBean extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "參數ID")
	private String type;
	
	@ApiModelProperty(value = "設定類型")
	private String detailType;

	@ApiModelProperty(value = "設定值")
	private String value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
