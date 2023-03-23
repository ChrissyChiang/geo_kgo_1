package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.taskMaintain.cityCoinSearch.rq.TaskMaintainCityCoinSearchRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.cityCoinSearch.rs.TaskMaintainCityCoinSearchRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.delete.rq.TaskMaintainDeleteRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.delete.rs.TaskMaintainDeleteRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.download.rq.TaskMaintainDownloadRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rq.TaskMaintainEditHomeRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.TaskMaintainEditHomeRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.home.rs.TaskMaintainHomeRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.onOff.rq.TaskMaintainOnOffRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.onOff.rs.TaskMaintainOnOffRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.organCase.rq.TaskMaintainOrganCaseRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.organCase.rs.TaskMaintainOrganCaseRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.query.rq.TaskMaintainQueryRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.query.rs.TaskMaintainQueryRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.save.rq.TaskMaintainSaveRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.save.rs.TaskMaintainSaveRs;

public interface TaskMaintainService {

	/**
	 * 任務及公告管理-任務以及公告查詢
	 * 
	 * @param rq
	 * @return
	 */
	TaskMaintainQueryRs taskMaintainQuery(TaskMaintainQueryRq rq);

	/**
	 * 任務及公告管理-任務維護(新增/修改)初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	TaskMaintainEditHomeRs taskMaintainEditHome(TaskMaintainEditHomeRq rq);

	/**
	 * 任務及公告管理-任務上下架
	 * 
	 * @param rq
	 * @return
	 */
	TaskMaintainOnOffRs taskMaintainOnOff(TaskMaintainOnOffRq rq);

	/**
	 * 任務及公告管理-初始畫面
	 * 
	 * @return
	 */
	TaskMaintainHomeRs taskMaintainHome();

	/**
	 * 任務及公告管理-任務維護儲存
	 * 
	 * @param rq
	 * @return
	 */
	TaskMaintainSaveRs taskMaintainSave(TaskMaintainSaveRq rq);

	/**
	 * 任務及公告管理-任務刪除
	 * 
	 * @param rq
	 * @return
	 */
	TaskMaintainDeleteRs taskMaintainDelete(TaskMaintainDeleteRq rq);

	/**
	 * 任務及公告管理– 機關帶出案件
	 * 
	 * @param rq
	 * @return
	 */
	TaskMaintainOrganCaseRs taskMaintainOrganCase(TaskMaintainOrganCaseRq rq);

	/**
	 * 任務及公告管理– 城市幣任務查詢
	 * 
	 * @param rq
	 * @return
	 */
	TaskMaintainCityCoinSearchRs taskMaintainCityCoinSearch(TaskMaintainCityCoinSearchRq rq);

	void taskMaintainDownload(TaskMaintainDownloadRq rq);

}
