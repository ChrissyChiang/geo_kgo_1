package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoCasesetType;
import gov.kcg.kgo.model.KgoCasesetTypePK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoCasesetTypeRepository extends BaseRepository<KgoCasesetType, KgoCasesetTypePK> {

	/**
	 * @param currentDateStr
	 * @return
	 */
	public List<KgoCasesetType> findByIdCaseSetId(String caseSetId);

	/**
	 * 
	 * @param caseSetId
	 */
	public void deleteByIdCaseSetId(String caseSetId);

}