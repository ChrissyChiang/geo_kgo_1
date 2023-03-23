package gov.kcg.kgo.viewModel.backend.taskMaintain.cityCoinSearch.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任務及公告管理– 城市幣任務查詢 View Form
 */
@ApiModel(description = "任務及公告管理– 城市幣任務查詢 View Form")
public class TaskMaintainCityCoinSearchViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 程式幣總點數 **/
	@ApiModelProperty(value = "程式幣總點數")
	private String totalPoint;

	/** 每次核發點數 **/
	@ApiModelProperty(value = "每次核發點數")
	private String point;

	public String getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(String totalPoint) {
		this.totalPoint = totalPoint;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

}
