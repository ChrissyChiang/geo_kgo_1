package gov.kcg.kgo.service;

import gov.kcg.kgo.enums.backend.ActFlowEnableEnum;
import gov.kcg.kgo.viewModel.backend.actFlowManager.delete.rq.ActFlowManagerDeleteRq;
import gov.kcg.kgo.viewModel.backend.actFlowManager.delete.rs.ActFlowManagerDeleteRs;
import gov.kcg.kgo.viewModel.backend.actFlowManager.home.rs.ActFlowManagerHomeActionRs;
import gov.kcg.kgo.viewModel.backend.actFlowManager.resendFlow.rq.ActFlowManagerResendFlowRq;
import gov.kcg.kgo.viewModel.backend.actFlowManager.save.rq.ActFlowManagerSaveRq;
import gov.kcg.kgo.viewModel.backend.actFlowManager.save.rs.ActFlowManagerSaveFlowRs;
import gov.kcg.kgo.viewModel.backend.actFlowManager.taskDetail.rq.ActFlowManagerTaskDetailActionRq;
import gov.kcg.kgo.viewModel.backend.actFlowManager.taskDetail.rs.ActFlowManagerTaskDetailActionRs;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

/**
 * 動態流程管理 Service
 *
 */
public interface BackEndFlowManagerService {

	/**
	 * 動態流程管理 - 畫面初始.
	 */
	public ActFlowManagerHomeActionRs homeAction();
	
	/**
	 * 取得任務明細.
	 *
	 * @param rq the rq
	 * @return the act flow manager task detail action rs
	 */
	public ActFlowManagerTaskDetailActionRs taskDetailAction(ActFlowManagerTaskDetailActionRq rq);
	
	/**
	 * 動態流程管理 - 儲存.
	 *
	 * @param rq the rq
	 * @return the api base response
	 */
	public ActFlowManagerSaveFlowRs saveAction (ActFlowManagerSaveRq rq, ActFlowEnableEnum enableEnum);
	
	/**
	 * 動態流程管理 - 修改重送流程(待處理匣案件修改送流程).
	 *
	 * @param rq the rq
	 * @return the api base response
	 */
	public ApiBaseResponse<BaseViewForm> resendFlowAction(ActFlowManagerResendFlowRq rq);
	
	/**
	 * 刪除流程.
	 *
	 * @param rq the rq
	 * @return the act flow manager delete rs
	 */
	public ActFlowManagerDeleteRs deleteAction(ActFlowManagerDeleteRq rq);

}
