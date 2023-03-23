package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "訂單取消 View Form")
public class CancelAppointmentViewForm extends BaseViewForm {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes="true : 驗證通過 可進行取消預約")
	private Boolean valid;
	@ApiModelProperty(notes="驗證訊息")
	private String confirmMsg;

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getConfirmMsg() {
		return confirmMsg;
	}

	public void setConfirmMsg(String confirmMsg) {
		this.confirmMsg = confirmMsg;
	}
}
