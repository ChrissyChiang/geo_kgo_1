package gov.kcg.kgo.viewModel.backend.questMaintain.onOff.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "常見問題維護-問題上下架 rq")
public class QuestionMaintainOnOffRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "問題序號")
	private Integer seq;

	@ApiModelProperty(value = "問題狀態")
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
