package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.organUnitManagement.comboBox.rq.OrganUnitManagementUnitComboBoxRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.comboBox.rs.OrganUnitManagementUnitComboBoxRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.delete.rq.OrganUnitManagementDeleteRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.delete.rs.OrganUnitManagementDeleteRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.edit.rq.OrganUnitManagementEditRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.edit.rs.OrganUnitManagementEditRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.editHome.rq.OrganUnitManagementEditHomeRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.editHome.rs.OrganUnitManagementEditHomeRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.home.rs.OrganUnitManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.organQuery.rq.OrganUnitManagementOrganQueryRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.organQuery.rs.OrganUnitManagementOrganQueryRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.query.rq.OrganUnitManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.query.rs.OrganUnitManagementQueryRs;

public interface OrganUnitManagementService {

	/**
	 * 機關科室管理-初始畫面
	 * 
	 * @param rq
	 * @return OrganUnitManagementHomeRs
	 */
	public OrganUnitManagementHomeRs organUnitManagementHome();

	/**
	 * 機關科室管理-取得單位下拉式選單
	 * 
	 * @param rq
	 * @return OrganUnitManagementUnitComboBoxRs
	 */
	public OrganUnitManagementUnitComboBoxRs unitComboBoxByOrganId(OrganUnitManagementUnitComboBoxRq rq);

	/**
	 * 機關科室管理-機關科室查詢
	 * 
	 * @param rq
	 * @return OrganUnitManagementQueryRs
	 */
	public OrganUnitManagementQueryRs organUnitManagementQuery(OrganUnitManagementQueryRq rq);

	/**
	 * 機關科室管理-機關科室維護(新增/修改)初始畫面
	 * 
	 * @param rq
	 * @return OrganUnitManagementEditHomeRs
	 */
	public OrganUnitManagementEditHomeRs organUnitManagementEditHome(OrganUnitManagementEditHomeRq rq);

	/**
	 * 機關科室管理-機關科室維護(新增/修改)
	 * 
	 * @param rq
	 * @return OrganUnitManagementEditRs
	 */
	public OrganUnitManagementEditRs organUnitManagementEdit(OrganUnitManagementEditRq rq);

	/**
	 * 機關科室管理-機關科室刪除
	 * 
	 * @param rq
	 * @return OrganUnitManagementDeleteRs
	 */
	public OrganUnitManagementDeleteRs organUnitManagementDelete(OrganUnitManagementDeleteRq rq);

	/**
	 * 機關科室管理-機關科室查詢
	 * 
	 * @param rq
	 * @return
	 */
	OrganUnitManagementOrganQueryRs organUnitManagementOrganQuery(OrganUnitManagementOrganQueryRq rq);

}
