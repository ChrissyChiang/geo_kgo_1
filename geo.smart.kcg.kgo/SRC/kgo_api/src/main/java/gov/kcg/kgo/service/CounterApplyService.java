package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.counterApply.delete.rq.CounterApplyDeleteRq;
import gov.kcg.kgo.viewModel.backend.counterApply.delete.rs.CounterApplyDeleteRs;
import gov.kcg.kgo.viewModel.backend.counterApply.home.rq.CounterApplyHomeRq;
import gov.kcg.kgo.viewModel.backend.counterApply.home.rs.CounterApplyHomeRs;
import gov.kcg.kgo.viewModel.backend.counterApply.save.rq.CounterApplySaveRq;
import gov.kcg.kgo.viewModel.backend.counterApply.save.rs.CounterApplySaveRs;

public interface CounterApplyService {

	/**
	 * 臨櫃申請-初始畫面
	 * 
	 * @param rq
	 * @return CounterApplyHomeRs
	 */
	public CounterApplyHomeRs counterApplyHome(CounterApplyHomeRq rq);

	/**
	 * 臨櫃申請-申請說明資料儲存
	 * 
	 * @param rq
	 * @return CounterApplySaveRs
	 */
	public CounterApplySaveRs counterApplySave(CounterApplySaveRq rq);

	/**
	 * 臨櫃申請-申請說明資料刪除
	 */
	CounterApplyDeleteRs counterApplyDelete(CounterApplyDeleteRq rq);

}
