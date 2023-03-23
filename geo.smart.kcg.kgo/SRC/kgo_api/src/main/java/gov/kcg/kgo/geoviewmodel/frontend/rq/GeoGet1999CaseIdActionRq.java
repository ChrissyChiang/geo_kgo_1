package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20210814 add
 *
 * 取得1999指定時間區間案件編號 rq
 */

@ApiModel(description = "取得1999指定時間區間案件編號 rq")
public class GeoGet1999CaseIdActionRq extends ApiCityCommonRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "起始日期區間(yyyy-MM-dd hh:mm:ss)", position=1)
	private String startTime;

	@ApiModelProperty(value = "結束日期區間(yyyy-MM-dd hh:mm:ss)", position=2)
	private String endTime;

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


}
