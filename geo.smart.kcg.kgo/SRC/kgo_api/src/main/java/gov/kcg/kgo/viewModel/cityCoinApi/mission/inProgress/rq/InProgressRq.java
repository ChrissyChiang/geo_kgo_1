package gov.kcg.kgo.viewModel.cityCoinApi.mission.inProgress.rq;

import gov.kcg.kgo.viewModel.cityCoinApi.base.request.CityCoinApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 城市幣任務 A1：POST /Mission/InProgress
 * 
 * @author TPIuser
 *
 */
@ApiModel(description = "InProgress rq")
public class InProgressRq extends CityCoinApiRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 所屬系統服務
	 */
	@ApiModelProperty(value = "所屬機關")
	private String OrganizerCode;

	public InProgressRq(String OrganizerCode) {
		this.OrganizerCode = OrganizerCode;
	}

	public String getOrganizerCode() {
		return OrganizerCode;
	}

	public void setOrganizerCode(String organizerCode) {
		OrganizerCode = organizerCode;
	}
}
