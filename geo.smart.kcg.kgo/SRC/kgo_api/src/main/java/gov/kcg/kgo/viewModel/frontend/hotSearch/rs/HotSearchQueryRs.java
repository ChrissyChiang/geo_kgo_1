package gov.kcg.kgo.viewModel.frontend.hotSearch.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.bean.HotSearchQueryViewForm;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "熱門搜尋-查詢 rs")
public class HotSearchQueryRs extends ApiBaseResponse<HotSearchQueryViewForm> {
}
