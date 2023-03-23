package gov.kcg.kgo.service;

import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.delete.rq.ThesaurusMaintainDeleteRq;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.delete.rs.ThesaurusMaintainDeleteRs;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.edit.rq.ThesaurusMaintainEditRq;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.edit.rs.ThesaurusMaintainEditRs;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.editHome.rq.ThesaurusMaintainEditHomeRq;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.editHome.rs.ThesaurusMaintainEditHomeRs;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.query.rq.ThesaurusMaintainQueryRq;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.query.rs.ThesaurusMaintainQueryRs;

public interface ThesaurusMaintainService {

	/**
	 * 常見詞庫維護-初始畫面
	 * 
	 * @return QuestionMaintainHomeRs
	 */
	public ThesaurusMaintainQueryRs thesaurusMaintainHome();

	/**
	 * 常見詞庫維護-問題查詢
	 * 
	 * @return ThesaurusMaintainQueryRs
	 */
	public ThesaurusMaintainQueryRs thesaurusMaintainQuery(ThesaurusMaintainQueryRq rq);

	/**
	 * 常見詞庫維護-問題維護(新增/修改)初始畫面
	 * 
	 * @return ThesaurusMaintainEditHomeRs
	 */
	public ThesaurusMaintainEditHomeRs thesaurusMaintainEditHome(ThesaurusMaintainEditHomeRq rq);

	/**
	 * 常見詞庫維護-問題維護(新增/修改)
	 * 
	 * @return ThesaurusMaintainEditRs
	 */
	public ThesaurusMaintainEditRs thesaurusMaintainEdit(ThesaurusMaintainEditRq rq);

	/**
	 * 常見詞庫維護-問題刪除
	 * 
	 * @return ThesaurusMaintainDeleteRs
	 */
	public ThesaurusMaintainDeleteRs thesaurusMaintainDelete(ThesaurusMaintainDeleteRq rq);

}
