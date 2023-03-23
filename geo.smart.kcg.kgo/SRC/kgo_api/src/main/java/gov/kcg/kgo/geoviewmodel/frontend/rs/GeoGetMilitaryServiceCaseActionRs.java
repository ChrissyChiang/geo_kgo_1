package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoMilitaryServiceCaseViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211115 add for 民政局五種服務轉成B流程
 * "提供民政局兵役類案件資料查詢(依時間區間) rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "兵役類案件 rs")
public class GeoGetMilitaryServiceCaseActionRs extends ApiBaseResponse<GeoMilitaryServiceCaseViewForm> {

}
