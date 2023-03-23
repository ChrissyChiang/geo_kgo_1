package gov.kcg.kgo.viewModel.backend.thesaurusMaintain.editHome.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "常見詞庫維護-問題維護(新增/修改)初始畫面 rq")
public class ThesaurusMaintainEditHomeRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "詞彙序號")
	private Integer seq;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
