package gov.kcg.kgo.geoviewmodel.backend.rs;

import gov.kcg.kgo.geoviewmodel.backend.rs.bean.SiteDeleteViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel; 



//場地刪除  Roy
@ApiModel(description = "場地刪除rs")
public class GeoSiteDeleteRs extends ApiBaseResponse<SiteDeleteViewForm> {
	private static final long serialVersionUID = 1L;
} 
