package gov.kcg.kgo.viewModel.backend.internetApply.acceptDate.home.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-受理機關設定-初始畫面 View Form
 */
@ApiModel(description = "網路申辦-受理機關設定-初始畫面 View Form")
public class InternetApplyAcceptDateHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 受理期間起日 **/
	@ApiModelProperty(value = "受理期間起日")
	private String dateStart;

	/** 受理期間訖日 **/
	@ApiModelProperty(value = "受理期間訖日")
	private String dateEnd;

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

}
