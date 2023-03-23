package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20210814 add
 *
 * 問卷清單 rq
 */

@ApiModel(description = "問卷清單 rq")
public class GeoQuestionnaireListRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關單位id", position=1)
	private String organId;

	@ApiModelProperty(value = "起始日期區間(yyyy-MM-dd hh:mm:ss)", position=2)
	private String startTime;

	@ApiModelProperty(value = "結束日期區間(yyyy-MM-dd hh:mm:ss)", position=3)
	private String endTime;

	@ApiModelProperty(value = "問卷題目關鍵字", position=4)
	private String keyWord;

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

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}


}
