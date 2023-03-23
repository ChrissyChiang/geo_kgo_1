package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.hotSearch.delete.rq.HotSearchDeleteRq;
import gov.kcg.kgo.viewModel.backend.hotSearch.delete.rs.HotSearchDeleteRs;
import gov.kcg.kgo.viewModel.backend.hotSearch.query.rq.HotSearchChangeRq;
import gov.kcg.kgo.viewModel.backend.hotSearch.save.rq.HotSearchSaveRq;
import gov.kcg.kgo.viewModel.backend.hotSearch.save.rs.HotSearchSaveRs;
import gov.kcg.kgo.viewModel.backend.hotSearch.saveAll.rq.HotSearchSaveAllRq;
import gov.kcg.kgo.viewModel.backend.hotSearch.saveAll.rs.HotSearchSaveAllRs;
import gov.kcg.kgo.viewModel.backend.hotSearch.home.rs.HotSearchHomeRs;

public interface HotSearchService {

	/**
	 * 熱門搜尋-初始畫面
	 * 
	 */
	HotSearchHomeRs hotSearchHome();

	/**
	 * 熱門搜尋-熱門設定-刪除
	 * 
	 * @param rq
	 * @return
	 */
	HotSearchDeleteRs hotSearchDelete(HotSearchDeleteRq rq);

	/**
	 * 熱門搜尋-熱門設定-儲存
	 * 
	 * @param rq
	 * @return
	 */
	HotSearchSaveRs hotSearchSave(HotSearchSaveRq rq);

	/**
	 * 熱門搜尋-整頁儲存
	 * 
	 * @param rq
	 * @return
	 */
	HotSearchSaveAllRs hotSearchSaveAll(HotSearchSaveAllRq rq);

	/**
	 * Geo 20220729 add 切換前台搜尋引擎
	 * @param rq
	 * @return
	 */
	void hotSearchChangeAction(HotSearchChangeRq rq);
}
