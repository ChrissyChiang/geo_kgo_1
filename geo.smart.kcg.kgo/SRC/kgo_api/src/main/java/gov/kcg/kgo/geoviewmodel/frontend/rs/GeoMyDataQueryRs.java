package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoMyDataQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20221009 add_Jim
 * MyData紀錄查詢(申請名稱、申請編號) rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "MyData紀錄查詢(申請名稱、申請編號) rs")
public class GeoMyDataQueryRs extends ApiBaseResponse<GeoMyDataQueryViewForm> {
}

