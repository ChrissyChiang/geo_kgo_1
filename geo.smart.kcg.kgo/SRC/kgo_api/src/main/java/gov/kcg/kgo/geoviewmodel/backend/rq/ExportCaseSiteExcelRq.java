package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "場地案件檢視(匯出EXCEL)")
public class ExportCaseSiteExcelRq extends ApiRequest {

	private static final long serialVersionUID = 1L;
	
	private String caseId;
	
	private String  in_caseSetCategory;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getIn_caseSetCategory() {
		return in_caseSetCategory;
	}

	public void setIn_caseSetCategory(String in_caseSetCategory) {
		this.in_caseSetCategory = in_caseSetCategory;
	}
	

}
