package gov.kcg.kgo.viewModel.cityCoinApi.mission.inProgress.rs.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import gov.kcg.kgo.viewModel.cityCoinApi.base.response.CityCoinBaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 城市幣API-取得城市幣任務清單資料 View Form
 */
@ApiModel(description = "城市幣API-取得城市幣任務清單資料 View Form")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InProgressViewForm extends CityCoinBaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 城市幣任務清單資料 **/
	@ApiModelProperty(value = "城市幣任務清單資料")
	private List<CityCoinMissionInfo> data;

	/** 數量 **/
	@ApiModelProperty(value = "數量")
	private Integer totalCount;

	public List<CityCoinMissionInfo> getData() {
		return data;
	}

	public void setData(List<CityCoinMissionInfo> data) {
		this.data = data;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
