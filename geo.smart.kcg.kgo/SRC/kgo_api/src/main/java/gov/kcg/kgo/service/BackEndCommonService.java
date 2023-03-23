package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.home.rq.AreaOrganSelectHomeRq;
import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.home.rs.AreaOrganSelectHomeRs;
import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.zipHome.rq.AreaOrganSelectZipHomeRq;
import gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.zipHome.rs.AreaOrganSelectZipHomeRs;
import gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rq.OrganComboBoxQueryRq;
import gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rs.OrganComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.common.organSelect.home.rq.OrganSelectHomeRq;
import gov.kcg.kgo.viewModel.backend.common.organSelect.home.rs.OrganSelectHomeRs;
import gov.kcg.kgo.viewModel.backend.common.organSelect.query.rq.OrganSelectQueryRq;
import gov.kcg.kgo.viewModel.backend.common.organSelect.query.rs.OrganSelectQueryRs;

import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.home.rq.OrganUnitUserSelectHomeRq;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.home.rs.OrganUnitUserSelectHomeRs;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.query.rq.OrganUnitUserSelectQueryRq;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.query.rs.OrganUnitUserSelectQueryRs;
import gov.kcg.kgo.viewModel.backend.common.unit.query.rq.UnitQueryRq;
import gov.kcg.kgo.viewModel.backend.common.unit.query.rs.UnitQueryRs;
import gov.kcg.kgo.viewModel.backend.common.unitComboBox.query.rq.UnitComboBoxQueryRq;
import gov.kcg.kgo.viewModel.backend.common.unitComboBox.query.rs.UnitComboBoxQueryRs;

/**
 * KGO 後端共通service.
 */
public interface BackEndCommonService {

	/**
	 * 共用-人員清單Popup 初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	OrganUnitUserSelectHomeRs organUnitUserSelectHomeAction(OrganUnitUserSelectHomeRq rq);

	/**
	 * 共用-人員清單Popup-查詢
	 * 
	 * @param rq
	 * @return
	 */
	OrganUnitUserSelectQueryRs organUnitUserSelectQueryAction(OrganUnitUserSelectQueryRq rq);

	/**
	 * 共用-人員清單Popup - 單位下拉式選單查詢
	 * 
	 * @param rq
	 * @return
	 */
	UnitComboBoxQueryRs unitComboBoxQueryAction(UnitComboBoxQueryRq rq);

	/**
	 * 共用-機關單位下拉選單
	 * 
	 * @param rq
	 * @return
	 */
	OrganComboBoxQueryRs organComboBoxQueryAction(OrganComboBoxQueryRq rq);

	/**
	 * 共用-機關列表Popup-初始畫面
	 */
	OrganSelectHomeRs organSelectHomeAction(OrganSelectHomeRq rq);

	/**
	 * 機關列表Popup-查詢
	 * 
	 * @param rq
	 * @return
	 */
	OrganSelectQueryRs organSelectQueryAction(OrganSelectQueryRq rq);

	/**
	 * 共用-區機關選擇-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	AreaOrganSelectHomeRs areaOrganSelectHomeAction(AreaOrganSelectHomeRq rq);

	/**
	 * 共用-區機關選擇-鄉鎮選擇-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	AreaOrganSelectZipHomeRs areaOrganSelectZipHomeAction(AreaOrganSelectZipHomeRq rq);

	/**
	 * 共用-機關科室查詢
	 */
	UnitQueryRs unitQueryAction(UnitQueryRq rq);

}
