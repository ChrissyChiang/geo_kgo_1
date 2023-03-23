package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoCaseSetModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20210814 add
 *
 * 取得1999案件編號清單 ViewForm
 */
@ApiModel(description = "取得1999案件編號清單  ViewForm")
public class GeoGet1999CaseIdActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件編號清單")
	private List<GeoCaseSetModel> caseList;

	public List<GeoCaseSetModel> getCaseList() {
		return caseList;
	}

	public void setCaseList(List<GeoCaseSetModel> caseList) {
		this.caseList = caseList;
	}
}
