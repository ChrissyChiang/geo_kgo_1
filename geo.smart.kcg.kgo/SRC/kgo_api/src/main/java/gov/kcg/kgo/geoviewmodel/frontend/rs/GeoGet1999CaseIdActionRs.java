package gov.kcg.kgo.geoviewmodel.frontend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoGet1999CaseIdActionViewForm;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20210814 add
 *
 * 取得1999指定時間區間案件編號 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "取得1999指定時間區間案件編號 rs")
public class GeoGet1999CaseIdActionRs extends ApiBaseResponse<GeoGet1999CaseIdActionViewForm> {

}
