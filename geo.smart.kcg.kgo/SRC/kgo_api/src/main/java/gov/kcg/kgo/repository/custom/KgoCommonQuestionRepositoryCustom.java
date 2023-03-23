package gov.kcg.kgo.repository.custom;

import java.util.List;

import gov.kcg.kgo.model.KgoCommonQuestion;

public interface KgoCommonQuestionRepositoryCustom {

	/**
	 * 問題資料搜尋
	 * 
	 * @param question
	 * @param publishDateStart
	 * @param publishDateEnd
	 * @return
	 */
	public List<KgoCommonQuestion> findByQuestionAndPublishTimeOrderBySeq(String question, String publishDateStart,
			String publishDateEnd);
}
