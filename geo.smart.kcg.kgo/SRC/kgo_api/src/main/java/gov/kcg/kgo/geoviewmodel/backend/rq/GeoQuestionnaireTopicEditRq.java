package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireTopicModel;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20210814 add
 *
 * 新增編輯問卷題組內容 rq
 */
@ApiModel(description = "新增編輯問卷題組內容 rq")
public class GeoQuestionnaireTopicEditRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "問卷題組清單(欄位詳GeoKgoQuestionnaireTopicModel)", required = true)
	private List<GeoKgoQuestionnaireTopicModel> modelList;

	public List<GeoKgoQuestionnaireTopicModel> getModelList() {
		return modelList;
	}

	public void setModelList(List<GeoKgoQuestionnaireTopicModel> modelList) {
		this.modelList = modelList;
	}

}
