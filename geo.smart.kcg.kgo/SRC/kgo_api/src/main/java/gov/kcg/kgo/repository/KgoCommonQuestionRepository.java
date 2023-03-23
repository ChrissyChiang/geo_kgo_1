package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoCommonQuestion;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoCommonQuestionRepositoryCustom;

public interface KgoCommonQuestionRepository
		extends BaseRepository<KgoCommonQuestion, Integer>, KgoCommonQuestionRepositoryCustom {

	/**
	 * 
	 * @param state
	 * @return
	 */
	public List<KgoCommonQuestion> findByStateOrderBySeqAsc(String state);
}
