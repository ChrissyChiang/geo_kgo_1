package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import java.util.List;

import gov.kcg.kgo.geomodel.GeokgoRentTimeInsertModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.ColumnViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "申請表單初始 View Form")
public class ApplyFormInitViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="驗證訊息")
	private  String validMsg;

	@ApiModelProperty(notes = "最早可預約時間 yyyy-MM-dd HH:mm:ss")
	private String earliestTime;

	@ApiModelProperty(notes = "最晚可預約時間 yyyy-MM-dd HH:mm:ss")
	private String lastiestTime;

	@ApiModelProperty(value="租借時段ID,多個,號分隔")
	private String rentTimeIds;

	@ApiModelProperty(notes = "案件欄位")
	private List<ColumnViewForm> columnData;


	public String getValidMsg() {return validMsg;}

	public void setValidMsg(String validMsg) {this.validMsg = validMsg;}

	public String getEarliestTime() {return earliestTime;}

	public void setEarliestTime(String earliestTime) {this.earliestTime = earliestTime;}

	public String getLastiestTime() {return lastiestTime;}

	public void setLastiestTime(String lastiestTime) {this.lastiestTime = lastiestTime;}

	public String getRentTimeIds() {return rentTimeIds;}

	public void setRentTimeIds(String rentTimeIds) {this.rentTimeIds = rentTimeIds;}

	public List<ColumnViewForm> getColumnData() {return columnData;}

	public void setColumnData(List<ColumnViewForm> columnData) {this.columnData = columnData;}
}
