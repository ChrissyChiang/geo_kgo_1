package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireTopicModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20210814 add
 *
 * 問卷題組 ViewForm
 */
@ApiModel(description = "問卷設定  ViewForm")
public class GeoQuestionnaireTopicListViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "問卷清單")
	private List<GeoKgoQuestionnaireTopicModel> topicList;

	public List<GeoKgoQuestionnaireTopicModel> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<GeoKgoQuestionnaireTopicModel> topicList) {
		this.topicList = topicList;
	}

}
