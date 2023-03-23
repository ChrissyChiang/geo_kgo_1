package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoCasesetMydata;
import gov.kcg.kgo.model.KgoCasesetMydataPK;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoCasesetMyDataRepositoryCustom;

public interface KgoCasesetMydataRepository
		extends BaseRepository<KgoCasesetMydata, KgoCasesetMydataPK>, KgoCasesetMyDataRepositoryCustom {

	public List<KgoCasesetMydata> findByIdCaseSetId(String caseSetId);

	/**
	 * 
	 * @param caseSetId
	 */
	public void deleteByIdCaseSetId(String caseSetId);
}
