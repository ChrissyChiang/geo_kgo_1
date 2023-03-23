package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20210814 add
 *
 * 編輯問卷主內容 rq
 */
@ApiModel(description = "編輯問卷主內容 rq")
public class GeoQuestionnaireEditRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "問卷id(新增不填,編輯時必填)")
	private String questionId;

	@ApiModelProperty(notes = "問卷主題(新增必填)", required = true)
	private String questionName;

	@ApiModelProperty(notes = "問卷描述", required = true)
	private String questionDesc;

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getQuestionDesc() {
		return questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

}
