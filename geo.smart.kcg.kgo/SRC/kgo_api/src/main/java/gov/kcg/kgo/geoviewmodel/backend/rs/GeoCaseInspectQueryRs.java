package gov.kcg.kgo.geoviewmodel.backend.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.CaseCaseInspectQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211030 add
 * 後台-案件稽核管理:案件查詢 rs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "後台-案件稽核管理:案件查詢 rs")
public class GeoCaseInspectQueryRs extends ApiBaseResponse<CaseCaseInspectQueryViewForm> {
}

