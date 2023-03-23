package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "前台-線上場地租借 - 場地資訊 rq")
public class GeoSiteDetailQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "場地ID，複數以,號分隔" ,required = true)
	private String siteMainId ;

	public String getSiteMainId() {
		return siteMainId;
	}

	public void setSiteMainId(String siteMainId) {
		this.siteMainId = siteMainId;
	}
}
