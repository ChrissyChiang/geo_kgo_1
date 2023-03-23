package gov.kcg.kgo.geoviewmodel.backend.rs;

import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoSiteQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * 場地查詢 rs  Roy
 */

@ApiModel(description = "場地查詢 rs")
public class SiteQueryRs extends ApiBaseResponse<GeoSiteQueryViewForm> {
}
