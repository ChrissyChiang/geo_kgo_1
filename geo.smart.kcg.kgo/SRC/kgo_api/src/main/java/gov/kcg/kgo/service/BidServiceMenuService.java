package gov.kcg.kgo.service;

import gov.kcg.kgo.dto.BidServiceMenuQueryDto;
import gov.kcg.kgo.viewModel.frontend.bidServiceMenu.home.rs.BidServiceMenuHomeRs;
import gov.kcg.kgo.viewModel.frontend.bidServiceMenu.query.rq.BidServiceMenuQueryRq;
import gov.kcg.kgo.viewModel.frontend.bidServiceMenu.query.rs.BidServiceMenuQueryRs;

import java.util.List;

public interface BidServiceMenuService {

	/**
	 * 申辦服務選單-初始畫面
	 * 
	 * @return
	 */
	public BidServiceMenuHomeRs bidServiceMenuHome();

	/**
	 * 申辦服務選單-申辦案件數查詢
	 * 
	 * @param rq
	 * @return
	 */
	BidServiceMenuQueryRs bidServiceMenuQuery(BidServiceMenuQueryRq rq);

	List<BidServiceMenuQueryDto> getBidServiceMenuCaseCountData(String mainType);


}
