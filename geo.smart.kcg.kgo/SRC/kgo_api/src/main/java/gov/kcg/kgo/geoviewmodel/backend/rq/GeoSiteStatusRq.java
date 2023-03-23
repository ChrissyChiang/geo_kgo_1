package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "修改場地上下架 rq")
public class GeoSiteStatusRq extends ApiRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主健")
	private String siteMainId;
	

	@ApiModelProperty(value = "啟用狀態")
	private Integer siteStatus;


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSiteMainId() {
		return siteMainId;
	}

	public void setSiteMainId(String siteMainId) {
		this.siteMainId = siteMainId;
	}

	public Integer getSiteStatus() {
		return siteStatus;
	}

	public void setSiteStatus(Integer siteStatus) {
		this.siteStatus = siteStatus;
	}
	
}
