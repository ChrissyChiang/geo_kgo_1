package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211115 add for 民政局五種服務轉成B流程
 *
 * 提供民政局案件編號查詢(依時間區間) rq
 */

@ApiModel(description = "提供民政局案件編號查詢(依時間區間) rq")
public class GeoGetCivilAffairsCaseActionRq extends ApiCityCommonRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "起始日期區間(yyyy-MM-dd hh:mm:ss)", position=1)
	private String startTime;

	@ApiModelProperty(value = "結束日期區間(yyyy-MM-dd hh:mm:ss)", position=2)
	private String endTime;

	@ApiModelProperty(value = "申辦項目主鍵值", position=3)
	private String CategoryID;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCategoryID() {
		return CategoryID;
	}

	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
	}
}
