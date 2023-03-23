package gov.kcg.kgo.service;

import gov.kcg.kgo.geoviewmodel.backend.rs.GeoOrganDiscountComboBoxRs;
import gov.kcg.kgo.viewModel.backend.accountManagement.accountHome.rs.AccountManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.home.rq.CaseManagementCaseOrderHomeRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.home.rs.CaseManagementCaseOrderHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.save.rq.CaseManagementCaseOrderSaveRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.save.rs.CaseManagementCaseOrderSaveRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseSave.rq.CaseManagementCaseSaveRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseSave.rs.CaseManagementCaseSaveRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.delete.rq.CaseManagementDeleteRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.delete.rs.CaseManagementDeleteRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rq.CaseManagementEditHomeRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rs.CaseManagementEditHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.CaseManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rq.CaseManagerComboBoxQueryRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rs.CaseManagerComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.organSelectQuery.rs.CaseManagementOrganSelectQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rq.CaseManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.CaseManagementQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.stateUpdate.rq.CaseManagementStatusUpdateRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.stateUpdate.rs.CaseManagementStatusUpdateRs;

public interface CaseManagementService {

	/**
	 * 服務案件清單-初始畫面
	 * 
	 * @param rq
	 * @return CaseManagementHomeRs
	 */
	public CaseManagementHomeRs caseManagementHome();

	/**
	 * 服務案件管理-服務管理者下拉式選單查詢
	 *
	 * @param rq
	 * @return
	 */
	CaseManagerComboBoxQueryRs caseManagerComboBoxQuery(CaseManagerComboBoxQueryRq rq);

	/**
	 * 服務案件清單-案件查詢
	 * 
	 * @param rq
	 * @return CaseManagementQueryRs
	 */
	public CaseManagementQueryRs caseManagementQuery(CaseManagementQueryRq rq);

	/**
	 * "服務案件清單-案件狀態更改 (上架/下架/立即受理)
	 * 
	 * @param rq
	 * @return CaseManagementStatusUpdateRs
	 */
	public CaseManagementStatusUpdateRs statusUpdate(CaseManagementStatusUpdateRq rq);

	/**
	 * 服務案件清單-案件刪除
	 * 
	 * @param rq
	 * @return CaseManagementDeleteRs
	 */
	public CaseManagementDeleteRs caseManagementDelete(CaseManagementDeleteRq rq);

	/**
	 * 服務案件清單-案件排序-初始畫面
	 * 
	 * @param rq
	 * @return CaseManagementCaseOrderHomeRs
	 */
	public CaseManagementCaseOrderHomeRs caseManagementCaseOrderHome(CaseManagementCaseOrderHomeRq rq);

	/**
	 * 服務案件清單-案件排序-修改
	 * 
	 * @param rq
	 * @return CaseManagementCaseOrderSaveRs
	 */
	public CaseManagementCaseOrderSaveRs caseManagementCaseOrderSave(CaseManagementCaseOrderSaveRq rq);

	/**
	 * 服務案件清單-案件維護-初始畫面
	 * 
	 * @param rq
	 * @return CaseManagementEditHomeRs
	 */
	public CaseManagementEditHomeRs caseManagementEditHome(CaseManagementEditHomeRq rq);

	/**
	 * 服務案件清單-案件維護-儲存
	 * 
	 * @param rq
	 * @return CaseManagementEditHomeRs
	 */
	public CaseManagementCaseSaveRs caseManagementCaseSave(CaseManagementCaseSaveRq rq);


	/**
	 * GEO 20211115 add for 跨機關檢視
	 * 服務案件清單-取得所有機關選單
	 *
	 * @return
	 */
	public CaseManagementOrganSelectQueryRs getOrganSelectComboBox();


	/**
	 * GEO 20221107 add for 服務編輯
	 * 服務編輯 - 繳費機關折扣下拉選單
	 * */
	public GeoOrganDiscountComboBoxRs getDiscountComboBox(CaseManagementEditHomeRq rq );
}
