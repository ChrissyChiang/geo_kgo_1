package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rq.BidCaseListHomeRq;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.BidCaseListHomeRs;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.query.rq.BidCaseListQueryRq;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.query.rs.BidCaseListQueryRs;

public interface BidCaseListService {

	/**
	 * 申辦案件清單-申辦案件資料查詢
	 * 
	 * @param rq
	 * @return
	 */
	BidCaseListQueryRs bidCaseListQuery(BidCaseListQueryRq rq);

	/**
	 * 申辦服務選單-初始畫面-機關服務
	 * 
	 * @return
	 */
	BidCaseListHomeRs bidCaseListHome(BidCaseListHomeRq rq);

}
