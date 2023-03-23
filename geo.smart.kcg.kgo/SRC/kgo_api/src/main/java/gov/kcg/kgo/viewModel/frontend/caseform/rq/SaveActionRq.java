package gov.kcg.kgo.viewModel.frontend.caseform.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveActionColumnViewForm;
import io.swagger.annotations.ApiModelProperty;

public class SaveActionRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "交易序號 (格式:yyyyMMddHHmmssSSS, ex: 20201114123531343)", example = "20201114123531343")
	protected String txId;

	@ApiModelProperty(notes = "機關單位代碼", example = "397007000Q")
	protected String orgId;

	@ApiModelProperty(notes = "案件種類ID")
	private String caseSetId;

	@ApiModelProperty(notes = "版本號")
	private Integer version;

	@ApiModelProperty(notes = "MyDataTxId 有走MyData才需傳")
	private String myDataTxId;

	@ApiModelProperty(notes = "受理機關，非必填")
	private String caseOrgan;

	@ApiModelProperty(notes = "場地預約時段主鍵")
	private String siteDetailTimeId;

	@ApiModelProperty(notes = "欄位")
	private List<SaveActionColumnViewForm> columnData;

	@ApiModelProperty(notes = "服務類型")
	private String caseSetCategory;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getMyDataTxId() {
		return myDataTxId;
	}

	public void setMyDataTxId(String myDataTxId) {
		this.myDataTxId = myDataTxId;
	}

	public String getCaseOrgan() {
		return caseOrgan;
	}

	public void setCaseOrgan(String caseOrgan) {
		this.caseOrgan = caseOrgan;
	}

	public List<SaveActionColumnViewForm> getColumnData() {
		return columnData;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSiteDetailTimeId() {return siteDetailTimeId;}

	public void setSiteDetailTimeId(String siteDetailTimeId) {this.siteDetailTimeId = siteDetailTimeId;}

	public void setColumnData(List<SaveActionColumnViewForm> columnData) {
		this.columnData = columnData;
	}

	public String getCaseSetCategory() {
		return caseSetCategory;
	}

	public void setCaseSetCategory(String caseSetCategory) {
		this.caseSetCategory = caseSetCategory;
	}
}


