package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoCasesetCheck;
import gov.kcg.kgo.model.KgoCasesetCheckPK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoCasesetCheckRepository extends BaseRepository<KgoCasesetCheck, KgoCasesetCheckPK> {

	/**
	 * 
	 * @param caseSetId
	 * @return
	 */
	public List<KgoCasesetCheck> findAllByIdCaseSetId(String caseSetId);

	/**
	 * 
	 * @param caseSetId
	 * @return
	 */
	public int deleteByIdCaseSetId(String caseSetId);

}
