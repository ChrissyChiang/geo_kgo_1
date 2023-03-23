package gov.kcg.kgo.viewModel.backend.template.query.rs;


import gov.kcg.kgo.viewModel.backend.taskMaintain.cityCoinSearch.rs.bean.TaskMaintainCityCoinSearchViewForm;
import gov.kcg.kgo.viewModel.backend.template.query.rs.bean.TemplateQueryActionViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * 後台表單維護– 查詢 rs
 */
@ApiModel(description = "後台表單維護– 查詢 rs")
public class TemplateQueryActionRs extends ApiBaseResponse<TemplateQueryActionViewForm> {
}
