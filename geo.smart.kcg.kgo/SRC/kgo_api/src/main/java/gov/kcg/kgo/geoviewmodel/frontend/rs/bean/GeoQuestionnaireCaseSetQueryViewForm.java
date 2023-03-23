package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireCaseSetTopicModel;
import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireCaseSetModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20210814 add
 *
 * 取得問卷主內容 ViewForm
 */
@ApiModel(description = "取得問卷主內容  ViewForm")
public class GeoQuestionnaireCaseSetQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "問卷主內容")
	private GeoKgoQuestionnaireCaseSetModel questionnaireMain;

	@ApiModelProperty(notes = "題組清單")
	private List<GeoKgoQuestionnaireCaseSetTopicModel> topicList;

	public GeoKgoQuestionnaireCaseSetModel getQuestionnaireMain() {
		return questionnaireMain;
	}

	public void setQuestionnaireMain(GeoKgoQuestionnaireCaseSetModel questionnaireMain) {
		this.questionnaireMain = questionnaireMain;
	}

	public List<GeoKgoQuestionnaireCaseSetTopicModel> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<GeoKgoQuestionnaireCaseSetTopicModel> topicList) {
		this.topicList = topicList;
	}
}
