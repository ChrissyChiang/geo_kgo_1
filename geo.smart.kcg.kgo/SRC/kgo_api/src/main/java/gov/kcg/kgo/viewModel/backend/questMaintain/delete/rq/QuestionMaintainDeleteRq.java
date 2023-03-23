package gov.kcg.kgo.viewModel.backend.questMaintain.delete.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "常見問題維護-問題刪除 rq")
public class QuestionMaintainDeleteRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "問題序號")
	private Integer seq;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
