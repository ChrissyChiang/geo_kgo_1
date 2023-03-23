package gov.kcg.kgo.viewModel.backend.classManagement.classOnOff.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "分類維護功能-類別上下架 rq")
public class ClassManagementClassOnOffRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "序號")
	private Integer seq;

	@ApiModelProperty(value = "狀態名稱 (on/off)")
	private String state;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
