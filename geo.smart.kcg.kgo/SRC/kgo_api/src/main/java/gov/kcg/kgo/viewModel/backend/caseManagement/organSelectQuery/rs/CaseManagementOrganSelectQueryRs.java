package gov.kcg.kgo.viewModel.backend.caseManagement.organSelectQuery.rs;

import gov.kcg.kgo.viewModel.backend.caseManagement.organSelectQuery.rs.bean.CaseManagementOrganSelectQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * GEO 20211115 add for 跨機關檢視
 * 服務案件清單-取得所有機關選單 rs
 */
@ApiModel(description = "服務案件清單-取得所有機關選單 rs")
public class CaseManagementOrganSelectQueryRs extends ApiBaseResponse<CaseManagementOrganSelectQueryViewForm> {

}
