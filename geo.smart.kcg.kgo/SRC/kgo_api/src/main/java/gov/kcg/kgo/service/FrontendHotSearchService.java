package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.frontend.hotSearch.rq.HotSearchGovernmentQueryRq;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rq.HotSearchQueryRq;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.FrontendHotSearchGovernmentRs;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.FrontendHotSearchHomeRs;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.FrontendHotSearchTypeRs;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.HotSearchQueryRs;

public interface FrontendHotSearchService {

    FrontendHotSearchHomeRs frontendHotSearchHome();

    HotSearchQueryRs hotSearchQuery(HotSearchQueryRq rq);

    /**
     * 20220729 前台切換搜尋引擎
     * 熱門搜尋-顯示搜尋引擎種類
     */
    FrontendHotSearchTypeRs frontendHotSearchType();

}
