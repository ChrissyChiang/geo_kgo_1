package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoFrontendUserInfoQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211113 add 前台使用者註冊
 * 前台-使用者帳號:取得註冊後使用者資訊 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "前台-使用者帳號:取得註冊後使用者資訊 rs")
public class GeoFrontendUserInfoQueryRs extends ApiBaseResponse<GeoFrontendUserInfoQueryViewForm> {
}

