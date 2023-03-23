package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.serviceApply.home.rs.ServiceApplyHomeRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rq.ServiceApplyPermissionActiveHomeRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rq.ServiceApplyPermissionUserComboBoxActionRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rs.ServiceApplyPermissionActiveHomeRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.home.rs.ServiceApplyPermissionUserComboBoxActionRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.save.rq.ServiceApplyPermissionActiveSaveRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.save.rs.ServiceApplyPermissionActiveSaveRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.query.rq.ServiceApplyQueryRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.query.rs.ServiceApplyQueryRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.edit.rq.ServiceApplyServiceCaseEditRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.edit.rs.ServiceApplyServiceCaseEditRs;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.home.rq.ServiceApplyServiceCaseHomeRq;
import gov.kcg.kgo.viewModel.backend.serviceApply.serviceCase.home.rs.ServiceApplyServiceCaseHomeRs;

public interface ServiceApplyService {

	/**
	 * 臨櫃申請-初始畫面
	 * 
	 */
	ServiceApplyHomeRs serviceApplyHome();

	/**
	 * 服務申請-申請案件查詢
	 * 
	 * @param rq
	 * @return
	 */
	ServiceApplyQueryRs serviceApplyQuery(ServiceApplyQueryRq rq);

	/**
	 * 服務申請-權限開通申請-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	ServiceApplyPermissionActiveHomeRs serviceApplyPermissionActiveHome(ServiceApplyPermissionActiveHomeRq rq);

	/**
	 * 服務申請-權限開通申請-儲存
	 * 
	 * @param rq
	 * @return
	 */
	ServiceApplyPermissionActiveSaveRs serviceApplyPermissionActiveSave(ServiceApplyPermissionActiveSaveRq rq);

	/**
	 * 服務申請-服務案件申請-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	ServiceApplyServiceCaseHomeRs serviceApplyServiceCaseHome(ServiceApplyServiceCaseHomeRq rq);

	/**
	 * 服務申請-服務案件申請-編輯
	 * 
	 * @param rq
	 * @return
	 */
	ServiceApplyServiceCaseEditRs serviceApplyServiceCaseEdit(ServiceApplyServiceCaseEditRq rq);

	/**
	 * GEO 20211115 add 非市府員工登入後台
	 * 服務申請-權限開通申請-初始畫面-審核者列表
	 * @param rq
	 * @return
	 */
	ServiceApplyPermissionUserComboBoxActionRs userComboBoxAction(ServiceApplyPermissionUserComboBoxActionRq rq);
}
