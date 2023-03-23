package gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務案件清單-案件排序-初始畫面
 */
@ApiModel(description = "服務案件清單-案件排序-初始畫面")
public class CaseManagementCaseOrderDataGrid {

	/** 案件順序 */
	@ApiModelProperty(notes = "案件順序")
	private Integer order;

	/** 案件種類ID */
	@ApiModelProperty(notes = "案件種類ID")
	private String caseSetId;

	/** 案件名稱 */
	@ApiModelProperty(notes = "案件名稱")
	private String caseSetName;

	/** 機關代碼 */
	@ApiModelProperty(notes = "機關代碼")
	private String organId;

	/** 機關名稱 */
	@ApiModelProperty(notes = "機關名稱")
	private String organName;

	/** 管理者姓名 */
	@ApiModelProperty(notes = "管理者姓名")
	private String managerName;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

}
