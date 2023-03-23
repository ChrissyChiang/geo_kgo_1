package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireTopicModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20210814 add
 *
 * 取得問卷題組主內容 ViewForm
 */
@ApiModel(description = "取得問卷題組主內容  ViewForm")
public class GeoQuestionnaireTopicQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "問卷題組主內容")
	private GeoKgoQuestionnaireTopicModel questionnaireTopic;

	public GeoKgoQuestionnaireTopicModel getQuestionnaireTopic() {
		return questionnaireTopic;
	}

	public void setQuestionnaireTopic(GeoKgoQuestionnaireTopicModel questionnaireTopic) {
		this.questionnaireTopic = questionnaireTopic;
	}

}
