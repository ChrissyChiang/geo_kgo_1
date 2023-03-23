package gov.kcg.kgo.geoviewmodel.frontend.rs;

import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoSiteDetailViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

@ApiModel(value="前台-線上場地租借-場地資訊 Rs")
public class CaseSetSiteDetailRs extends ApiBaseResponse<GeoSiteDetailViewForm> {
    private static final long serialVersionUID = 1L;
}

