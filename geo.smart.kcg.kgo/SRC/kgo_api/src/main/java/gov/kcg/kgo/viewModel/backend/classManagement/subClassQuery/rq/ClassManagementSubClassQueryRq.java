package gov.kcg.kgo.viewModel.backend.classManagement.subClassQuery.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "分類維護功能-取得次類別 rq")
public class ClassManagementSubClassQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "隸屬主類別代碼")
	private String mainType;

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

}
