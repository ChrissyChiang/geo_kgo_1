package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoMoicaDataViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211108 add
 * 為A流程服務提供取得自然人憑證登入資訊 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "為A流程服務提供取得自然人憑證登入資訊 rs")
public class GeoMoicaDataQueryRs extends ApiBaseResponse<GeoMoicaDataViewForm> {
}
