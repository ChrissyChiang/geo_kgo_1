package gov.kcg.kgo.viewModel.backend.classManagement.classDelete.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "分類維護功能-類別刪除 rq")
public class ClassManagementClassDeleteRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "序號")
	private Integer seq;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}
