package gov.kcg.kgo.viewModel.backend.hotSearch.delete.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "熱門搜尋-熱門設定-刪除 rq")
public class HotSearchDeleteRq extends ApiRequest {

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
