package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCaseSetInfoViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCaseSetListViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * Geo 20220729 前台切換搜尋引擎
 * 提供目前所有上架狀態的申辦案件資料，讓其他廠商可爬蟲使用
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "提供目前所有上架狀態的申辦案件資料，讓其他廠商可爬蟲使用 rs")
public class GeoOfferCaseSetInfoRs extends ApiBaseResponse<GeoCaseSetInfoViewForm> {
}
