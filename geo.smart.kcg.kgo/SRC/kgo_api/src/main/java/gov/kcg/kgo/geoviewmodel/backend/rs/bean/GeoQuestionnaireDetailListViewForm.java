package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireTopicDetailModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20210814 add
 *
 * 問卷子題目 ViewForm
 */
@ApiModel(description = "問卷子題目  ViewForm")
public class GeoQuestionnaireDetailListViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "子題目清單")
	private List<GeoKgoQuestionnaireTopicDetailModel>  detailList;

	public List<GeoKgoQuestionnaireTopicDetailModel> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<GeoKgoQuestionnaireTopicDetailModel> detailList) {
		this.detailList = detailList;
	}

}
