package gov.kcg.kgo.geoviewmodel.backend.rs;

import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoSiteEditViewForm;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoSiteSaveViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "場地新增/編輯 rs")
public class GeoSiteEditRs extends ApiBaseResponse<GeoSiteEditViewForm> {
}
