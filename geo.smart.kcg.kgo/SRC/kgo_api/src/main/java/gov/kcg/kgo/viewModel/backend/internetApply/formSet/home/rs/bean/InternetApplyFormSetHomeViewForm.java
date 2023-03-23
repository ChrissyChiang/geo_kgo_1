package gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-表單設定-初始畫面 View Form
 */
@ApiModel(description = "網路申辦-表單設定-初始畫面 View Form")
public class InternetApplyFormSetHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 服務案件編號 **/
	@ApiModelProperty(value = "服務案件編號")
	private String caseSetId;

	/** 表單群組&欄位版號 **/
	@ApiModelProperty(value = "表單群組&欄位版號")
	private Integer version;

	/** 案件表單設定所有群組欄位資料集合 **/
	@ApiModelProperty(value = "案件表單設定所有群組欄位資料集合")
	private List<InternetApplyFormSetHomeDataGrid> grid;

	/** GEO 20211109 add 機關審核表單 */
	@ApiModelProperty(value = "機關審核表單表單群組&欄位版號")
	private Integer organFormVersion;

	@ApiModelProperty(value = "案件機關審核表單設定所有群組欄位資料集合")
	private List<InternetApplyFormSetHomeOrganDataGrid> organFormGrid;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public List<InternetApplyFormSetHomeDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<InternetApplyFormSetHomeDataGrid> grid) {
		this.grid = grid;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<InternetApplyFormSetHomeOrganDataGrid> getOrganFormGrid() {
		return organFormGrid;
	}

	public void setOrganFormGrid(List<InternetApplyFormSetHomeOrganDataGrid> organFormGrid) {
		this.organFormGrid = organFormGrid;
	}

	public Integer getOrganFormVersion() {
		return organFormVersion;
	}

	public void setOrganFormVersion(Integer organFormVersion) {
		this.organFormVersion = organFormVersion;
	}
}
