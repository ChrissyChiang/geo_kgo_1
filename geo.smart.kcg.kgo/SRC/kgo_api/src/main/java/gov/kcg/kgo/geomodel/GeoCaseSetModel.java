package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveFileViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ApiModel(description = "後台案件處理-1999服務案件資料集合")
public class GeoCaseSetModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "案件ID")
	private String caseId;

	@ApiModelProperty(notes = "服務ID")
	private String CaseSetId;

	@ApiModelProperty(notes = "服務名稱")
	private String caseSetName;

	@ApiModelProperty(notes = "建立日期")
	private Timestamp applyDate;

	@ApiModelProperty(notes = "案件欄位集合")
	private List<GeoCaseColumnModel> caseColumns;

	@ApiModelProperty(notes = "附件檔案數量")
	private int dataCount;

	@ApiModelProperty(notes = "附件檔案集合")
	private List<SaveFileViewForm> dataList;


	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseSetId() {
		return CaseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		CaseSetId = caseSetId;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getApplyDate() {
		Date date = new Date();
		date.setTime(applyDate.getTime());
		String formattedDate = new SimpleDateFormat("yyyyMMddhhmmssSSS ").format(date);
		return formattedDate;
	}

	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
	}

	public List<GeoCaseColumnModel> getCaseColumns() {
		return caseColumns;
	}

	public void setCaseColumns(List<GeoCaseColumnModel> caseColumns) {
		this.caseColumns = caseColumns;
	}

	public int getDataCount() {
		return dataCount;
	}

	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}

	public List<SaveFileViewForm> getDataList() {
		return dataList;
	}

	public void setDataList(List<SaveFileViewForm> dataList) {
		this.dataList = dataList;
	}


}
