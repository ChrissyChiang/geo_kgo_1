package gov.kcg.kgo.viewModel.backend.classManagement.classQuery.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "分類維護功能-主畫面搜尋 rq")
public class ClassManagementClassQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "次類別名稱")
	private String name;

	@ApiModelProperty(value = "主類別代碼")
	private String mainType;

	@ApiModelProperty(value = "上下架時間")
	private String[] publishTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

	public String[] getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String[] publishTime) {
		this.publishTime = publishTime;
	}

}
