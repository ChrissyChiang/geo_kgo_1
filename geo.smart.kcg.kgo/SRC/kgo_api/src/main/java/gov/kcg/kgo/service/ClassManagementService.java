package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.classManagement.classDelete.rq.ClassManagementClassDeleteRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classDelete.rs.ClassManagementClassDeleteRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classHome.rs.ClassManagementClassHomeRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classOnOff.rq.ClassManagementClassOnOffRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classOnOff.rs.ClassManagementClassOnOffRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classQuery.rq.ClassManagementClassQueryRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classQuery.rs.ClassManagementClassQueryRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdate.rq.ClassManagementClassUpdateRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdate.rs.ClassManagementClassUpdateRs;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdateHome.rq.ClassManagementClassUpdateHomeRq;
import gov.kcg.kgo.viewModel.backend.classManagement.classUpdateHome.rs.ClassManagementClassUpdateHomeRs;
import gov.kcg.kgo.viewModel.backend.classManagement.subClassQuery.rq.ClassManagementSubClassQueryRq;
import gov.kcg.kgo.viewModel.backend.classManagement.subClassQuery.rs.ClassManagementSubClassQueryRs;

public interface ClassManagementService {

	/**
	 * 分類維護功能-畫面初始
	 * 
	 * @param rq
	 * @return ClassManagementClassHomeRs
	 */
	public ClassManagementClassHomeRs classManagementClassHome();

	/**
	 * 分類維護-主畫面搜尋
	 * 
	 * @param rq
	 * @return ClassManagementClassQueryRs
	 */
	public ClassManagementClassQueryRs classManagementClassQuery(ClassManagementClassQueryRq rq);

	/**
	 * 分類維護功能-類別新增/維護-畫面初始
	 * 
	 * @param rq
	 * @return ClassManagementClassUpdateRs
	 */
	public ClassManagementClassUpdateHomeRs classManagementClassUpdateHome(ClassManagementClassUpdateHomeRq rq);

	/**
	 * 分類維護功能-類別新增/維護
	 * 
	 * @param rq
	 * @return ClassManagementClassUpdateRs
	 */
	public ClassManagementClassUpdateRs classManagementClassUpdate(ClassManagementClassUpdateRq rq);

	/**
	 * 分類維護功能-類別刪除
	 * 
	 * @param rq
	 * @return ClassManagementClassDeleteRs
	 */
	public ClassManagementClassDeleteRs classManagementClassDelete(ClassManagementClassDeleteRq rq);

	/**
	 * 分類維護功能-上下架
	 * 
	 * @param rq
	 * @return ClassManagementClassOnOffRs
	 */
	public ClassManagementClassOnOffRs classManagementClassOnOff(ClassManagementClassOnOffRq rq);

	/**
	 * 次分類名稱下拉式選單
	 * 
	 * @param rq
	 * @return
	 */
	ClassManagementSubClassQueryRs classManagementSubClassQuery(ClassManagementSubClassQueryRq rq);

}
