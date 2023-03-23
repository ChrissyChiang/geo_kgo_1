package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoCasesetColumnChild;
import gov.kcg.kgo.model.KgoCasesetColumnChildPK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoCasesetColumnChildRepository
		extends BaseRepository<KgoCasesetColumnChild, KgoCasesetColumnChildPK> {

	/**
	 * 取得服務申辦流程(SA) -> KGO_CASE_MAIN 欄位資料&填入的資料
	 *
	 * @param caseId the case id
	 * @return the sa case extra data
	 */
	public List<KgoCasesetColumnChild> findByIdCaseSetIdAndIdVersionAndIdColumnIdOrderByOrderNumAsc(String caseSetId, Integer version, String columnId);

	public List<KgoCasesetColumnChild> findByIdCaseSetIdAndIdVersion(String caseSetId, Integer version);

	int deleteByIdCaseSetId(String caseSetId);

}
