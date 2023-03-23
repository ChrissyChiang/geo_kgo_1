package gov.kcg.kgo.geoviewmodel.backend.rs;

import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoTemplateOranViewViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211108 add for 機關審核表單
 * 後台表單維護–取得機關審核表單初始欄位明細 rs
 */
@ApiModel(description = "後台表單維護– 明細 rs")
public class GeoTemplateOrganViewActionRs extends ApiBaseResponse<GeoTemplateOranViewViewForm> {
}