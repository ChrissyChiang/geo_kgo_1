package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireMainModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20210814 add
 *
 * 取得問卷主內容 ViewForm
 */
@ApiModel(description = "取得問卷主內容  ViewForm")
public class GeoQuestionnaireQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "問卷主內容")
	private GeoKgoQuestionnaireMainModel questionnaireMain;

	public GeoKgoQuestionnaireMainModel getQuestionnaireMain() {
		return questionnaireMain;
	}

	public void setQuestionnaireMain(GeoKgoQuestionnaireMainModel questionnaireMain) {
		this.questionnaireMain = questionnaireMain;
	}

}
