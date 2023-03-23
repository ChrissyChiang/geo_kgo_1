package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.paramSet.home.rs.ParamSetHomeActionRs;
import gov.kcg.kgo.viewModel.backend.paramSet.save.rq.ParamSetSaveActionRq;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

/**
 * 後參數設定 Service
 *
 */
public interface BackEndParamSetService {

	/**
	 * 參數設定 - 畫面初始.
	 */
	public ParamSetHomeActionRs homeAction();
	
	/**
	 * 參數設定 - 儲存.
	 *
	 * @param rq the rq
	 * @return the api base response
	 */
	public ApiBaseResponse<BaseViewForm> saveAction (ParamSetSaveActionRq rq);

}
