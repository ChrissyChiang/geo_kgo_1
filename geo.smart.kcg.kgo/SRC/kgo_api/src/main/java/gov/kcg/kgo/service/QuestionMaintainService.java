package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.questMaintain.delete.rq.QuestionMaintainDeleteRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.delete.rs.QuestionMaintainDeleteRs;
import gov.kcg.kgo.viewModel.backend.questMaintain.edit.rq.QuestionMaintainEditRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.edit.rs.QuestionMaintainEditRs;
import gov.kcg.kgo.viewModel.backend.questMaintain.editHome.rq.QuestionMaintainEditHomeRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.editHome.rs.QuestionMaintainEditHomeRs;
import gov.kcg.kgo.viewModel.backend.questMaintain.onOff.rq.QuestionMaintainOnOffRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.onOff.rs.QuestionMaintainOnOffRs;
import gov.kcg.kgo.viewModel.backend.questMaintain.query.rq.QuestionMaintainQueryRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.query.rs.QuestionMaintainQueryRs;

public interface QuestionMaintainService {

	/**
	 * 常見問題維護-初始畫面
	 * 
	 * @return QuestionMaintainHomeRs
	 */
	public QuestionMaintainQueryRs questionMaintainHome();

	/**
	 * 常見問題維護-問題查詢
	 * 
	 * @return QuestionMaintainQueryRs
	 */
	public QuestionMaintainQueryRs questionMaintainQuery(QuestionMaintainQueryRq rq);

	/**
	 * 常見問題維護-問題維護(新增/修改)初始畫面
	 * 
	 * @return QuestionMaintainEditRs
	 */
	public QuestionMaintainEditHomeRs questionMaintainEditHome(QuestionMaintainEditHomeRq rq);

	/**
	 * 常見問題維護-問題維護(新增/修改)
	 * 
	 * @return QuestionMaintainEditRs
	 */
	public QuestionMaintainEditRs questionMaintainEdit(QuestionMaintainEditRq rq);

	/**
	 * 常見問題維護-問題上下架
	 * 
	 * @return QuestionMaintainOnOffRs
	 */
	public QuestionMaintainOnOffRs questionMaintainOnOff(QuestionMaintainOnOffRq rq);

	/**
	 * 常見問題維護-問題刪除
	 * 
	 * @return QuestionMaintainDeleteRs
	 */
	public QuestionMaintainDeleteRs questionMaintainDelete(QuestionMaintainDeleteRq rq);

}
