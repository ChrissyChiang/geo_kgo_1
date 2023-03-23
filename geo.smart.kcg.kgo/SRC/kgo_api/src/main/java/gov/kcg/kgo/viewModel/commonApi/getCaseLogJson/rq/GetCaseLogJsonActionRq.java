package gov.kcg.kgo.viewModel.commonApi.getCaseLogJson.rq;

import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "提供案件軌跡紀錄json log rq")
public class GetCaseLogJsonActionRq extends ApiCityCommonRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "查詢起日 yyyy/MM/dd", position=2, example="2020/12/10")
	private String startDate;

	@ApiModelProperty(value = "查詢迄日 yyyy/MM/dd", position=3, example="2020/12/11")
	private String endDate;
	
	@ApiModelProperty(value = "查詢機關代碼", position=4, example="397007000Q")
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
