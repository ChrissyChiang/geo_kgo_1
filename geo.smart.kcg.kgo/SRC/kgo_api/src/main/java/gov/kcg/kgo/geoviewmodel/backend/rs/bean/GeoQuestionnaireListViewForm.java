package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireMainModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20210814 add
 *
 * 問卷設定 ViewForm
 */
@ApiModel(description = "問卷設定  ViewForm")
public class GeoQuestionnaireListViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "問卷清單")
	private List<GeoKgoQuestionnaireMainModel> questionnaireList;

	public List<GeoKgoQuestionnaireMainModel> getQuestionnaireList() {
		return questionnaireList;
	}

	public void setQuestionnaireList(List<GeoKgoQuestionnaireMainModel> questionnaireList) {
		this.questionnaireList = questionnaireList;
	}
}
