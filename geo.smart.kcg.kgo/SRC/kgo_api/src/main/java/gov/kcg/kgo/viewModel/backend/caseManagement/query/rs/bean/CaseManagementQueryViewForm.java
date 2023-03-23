package gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務案件清單-案件查詢 View Form
 */
@ApiModel(description = "服務案件清單-案件查詢 View Form")
public class CaseManagementQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 案件搜尋結果清單 **/
	@ApiModelProperty(value = "列出所有案件資料")
	private List<CaseManagementQueryDataGrid> grid;

	@ApiModelProperty(value = "caseApply歷程圖")
	private String caseImage;

	@ApiModelProperty(value = "authApply歷程圖")
	private String authImage;

	public String getCaseImage() {
		return caseImage;
	}

	public void setCaseImage(String caseImage) {
		this.caseImage = caseImage;
	}

	public String getAuthImage() {
		return authImage;
	}

	public void setAuthImage(String authImage) {
		this.authImage = authImage;
	}

	public List<CaseManagementQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<CaseManagementQueryDataGrid> grid) {
		this.grid = grid;
	}

}
