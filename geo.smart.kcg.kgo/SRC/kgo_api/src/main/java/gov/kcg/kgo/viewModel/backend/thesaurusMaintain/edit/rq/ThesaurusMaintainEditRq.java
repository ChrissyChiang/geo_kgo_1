package gov.kcg.kgo.viewModel.backend.thesaurusMaintain.edit.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "常見詞庫維護-問題維護(新增/修改) rq")
public class ThesaurusMaintainEditRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "詞彙序號")
	private Integer seq;

	@ApiModelProperty(value = "詞彙名稱")
	private String title;

	@ApiModelProperty(value = "詞彙內容")
	private String word;

	public Integer getSeq() {
		return seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
