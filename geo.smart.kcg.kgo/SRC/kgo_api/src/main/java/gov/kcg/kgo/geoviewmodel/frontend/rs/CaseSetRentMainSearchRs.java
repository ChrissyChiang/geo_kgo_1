package gov.kcg.kgo.geoviewmodel.frontend.rs;


import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCasesetRentMainViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "前台-線上預約臨櫃:搜尋線上租借服務清單 rs")
public class CaseSetRentMainSearchRs extends ApiBaseResponse<GeoCasesetRentMainViewForm> {
    private static final long serialVersionUID = 1L;
}

