package gov.kcg.kgo.viewModel.backend.questMaintain.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "常見問題維護-問題查詢 rq")
public class QuestionMaintainQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "問題")
	private String question;

	@ApiModelProperty(value = "上下架日期")
	private String[] publishDate;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String[] publishDate) {
		this.publishDate = publishDate;
	}

}
