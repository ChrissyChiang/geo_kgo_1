package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "修改場地上下架 rq")
public class GeoSiteDeleteRq extends ApiRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "場地編號")
	private String siteMainId;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSiteMainId() {
		return siteMainId;
	}

	public void setSiteMainId(String siteMainId) {
		this.siteMainId = siteMainId;
	}

}
