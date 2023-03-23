package gov.kcg.kgo.viewModel.frontend.caseform.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "前臺 案件表單申請-初始化")
public class HomeActionViewForm extends BaseViewForm {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "案件服編號")
	private String caseSetId;

	@ApiModelProperty(notes = "案件名稱")
	private String caseSetName;

	@ApiModelProperty(notes = "作業流程")
	private String caseType;

	@ApiModelProperty(notes = "作業流程分類")
	private String caseFlowType;

	@ApiModelProperty(notes = "版本號")
	private Integer version;

	@ApiModelProperty(notes = "是否為需要連 MyData")
	private Boolean isMyData;

	@ApiModelProperty(notes = "是否為需要連 城市資料平台")
	private Boolean isKgoCity;

	@ApiModelProperty(notes = "案件群組")
	private List<GroupViewForm> grid;

	@ApiModelProperty(notes = "欄位資料")
	private List<OptionViewForm> options;

	@ApiModelProperty(notes = "受理設定")
	private String acceptSet;

	@ApiModelProperty(notes = "受理機關")
	private ComboBox caseOrganComboBox;
	
	@ApiModelProperty(notes = "申請機關")
	private String caseOrganApply;	

	public String getCaseSetId() {
		return this.caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getCaseSetName() {
		return this.caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getCaseType() {
		return this.caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCaseFlowType() {
		return this.caseFlowType;
	}

	public void setCaseFlowType(String caseFlowType) {
		this.caseFlowType = caseFlowType;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<OptionViewForm> getOptions() {
		return this.options;
	}

	public void setOptions(List<OptionViewForm> options) {
		this.options = options;
	}

	public ComboBox getCaseOrganComboBox() {
		return this.caseOrganComboBox;
	}

	public void setCaseOrganComboBox(ComboBox caseOrganComboBox) {
		this.caseOrganComboBox = caseOrganComboBox;
	}

	public List<GroupViewForm> getGrid() {
		return grid;
	}

	public void setGrid(List<GroupViewForm> grid) {
		this.grid = grid;
	}

	public Boolean getIsMyData() {
		return isMyData;
	}

	public void setIsMyData(Boolean isMyData) {
		this.isMyData = isMyData;
	}

	public Boolean getIsKgoCity() {
		return isKgoCity;
	}

	public void setIsKgoCity(Boolean isKgoCity) {
		this.isKgoCity = isKgoCity;
	}

	public String getAcceptSet() {
		return acceptSet;
	}

	public void setAcceptSet(String acceptSet) {
		this.acceptSet = acceptSet;
	}

	public String getCaseOrganApply() {
		return caseOrganApply;
	}

	public void setCaseOrganApply(String caseOrganApply) {
		this.caseOrganApply = caseOrganApply;
	}

}
