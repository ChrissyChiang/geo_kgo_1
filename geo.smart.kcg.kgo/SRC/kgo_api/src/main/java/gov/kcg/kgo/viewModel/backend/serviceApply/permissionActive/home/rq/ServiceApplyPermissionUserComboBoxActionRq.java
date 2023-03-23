package gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211115 add 非市府員工登入後台
 * 服務申請-權限開通申請-初始畫面-審核者列表
 */
@ApiModel(description = "服務申請-權限開通申請-初始畫面-審核者列表 rq")
public class ServiceApplyPermissionUserComboBoxActionRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關id", required = true)
	private String organId;

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
}
