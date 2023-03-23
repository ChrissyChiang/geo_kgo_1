package gov.kcg.kgo.viewModel.backend.kgoCaselog.exportExcel.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台 - 案件軌跡紀錄 - 匯出報表 rq")
public class KgoCaseLogExportExcelActionRq extends ApiRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "查詢起日 yyyy/MM/dd")
	private String startDate;

	@ApiModelProperty(value = "查詢迄日 yyyy/MM/dd")
	private String endDate;
	
	@ApiModelProperty(value = "機關/單位代碼")
	private String organId;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
}
