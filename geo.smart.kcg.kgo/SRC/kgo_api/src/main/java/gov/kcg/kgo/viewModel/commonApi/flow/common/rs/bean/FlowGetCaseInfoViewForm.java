package gov.kcg.kgo.viewModel.commonApi.flow.common.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModelProperty;

/**
 * 提供既有案件服務撈取案件資料 共通Form
 */
public class FlowGetCaseInfoViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件編號", position=1, example="A2020111000001")
	private String caseId;
	
	@ApiModelProperty(value = "申辦名稱", position=2, example="申辦名稱")
	private String caseSetName;
	
	/** 申請時間 */
	@ApiModelProperty(notes = "申辦日期", position=3, example="20201201130032")
	private String applyDate;
	
	@ApiModelProperty(notes = "申辦欄位資料", position=4)
	private List<CaseColumnDetail> caseColumns;
	
	@ApiModelProperty(notes = "myData資料集", position=5)
	private List<MyDataFileBo> dataList;
	
	@ApiModelProperty(notes = "分處", position=6)
	private String f3Name;
	
	@ApiModelProperty(notes = "案件資料 base64 string", position=7)
	private String caseInfoPdfBase64Str;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public List<CaseColumnDetail> getCaseColumns() {
		return caseColumns;
	}

	public void setCaseColumns(List<CaseColumnDetail> caseColumns) {
		this.caseColumns = caseColumns;
	}

	public List<MyDataFileBo> getDataList() {
		return dataList;
	}

	public void setDataList(List<MyDataFileBo> dataList) {
		this.dataList = dataList;
	}

	public String getF3Name() {
		return f3Name;
	}

	public void setF3Name(String f3Name) {
		this.f3Name = f3Name;
	}

	public String getCaseInfoPdfBase64Str() {
		return caseInfoPdfBase64Str;
	}

	public void setCaseInfoPdfBase64Str(String caseInfoPdfBase64Str) {
		this.caseInfoPdfBase64Str = caseInfoPdfBase64Str;
	}
}
