package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoCasesetManager;
import gov.kcg.kgo.model.KgoCasesetManagerPK;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoCasesetManagerRepositoryCustom;

public interface KgoCasesetManagerRepository
		extends BaseRepository<KgoCasesetManager, KgoCasesetManagerPK>, KgoCasesetManagerRepositoryCustom {

	/**
	 * 
	 * @param caseSetId
	 * @return
	 */
	public List<KgoCasesetManager> findByIdCaseSetId(String caseSetId);

	/**
	 * 
	 * @param caseSetId
	 */
	public void deleteByIdCaseSetId(String caseSetId);
}
