package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台-線上預約臨櫃-編輯：表單儲存更版 View Form
 */
@ApiModel(description = "後台-線上預約臨櫃-編輯：表單儲存更版 View Form")
public class GeoAppointmentFormGroupColumnEditViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "進版號")
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
