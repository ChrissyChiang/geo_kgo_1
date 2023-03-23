package gov.kcg.kgo.viewModel.backend.kgoLog.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台 - 軌跡紀錄 - 查詢 rq")
public class KgoLogQueryActionRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "系統類型 (B:後台, F:前台)")
	private String systemType;
	
	@ApiModelProperty(value = "查詢起日 yyyy/MM/dd")
	private String startDate;

	@ApiModelProperty(value = "查詢迄日 yyyy/MM/dd")
	private String endDate;

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

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
}
