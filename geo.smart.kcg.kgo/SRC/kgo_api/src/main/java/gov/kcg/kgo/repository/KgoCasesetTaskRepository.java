package gov.kcg.kgo.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.model.KgoCasesetTask;
import gov.kcg.kgo.model.KgoCasesetTaskPK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoCasesetTaskRepository extends BaseRepository<KgoCasesetTask, KgoCasesetTaskPK> {

	/**
	 * 
	 * @param ActivitySeq
	 * @return
	 */
	@Modifying
	@Query(value = "Delete From KgoCasesetTask Where ActivitySeq = :activitySeq")
	public void deleteByActivitySeq(@Param("activitySeq") int activitySeq);

	/**
	 * 
	 * @param activitySeq
	 * @return
	 */
	@Query(value = "Select case when count(*) > 0 then true else false end From KgoCasesetTask Where ActivitySeq = :activitySeq")
	public boolean existsActivitySeqCustomQuery(@Param("activitySeq") int activitySeq);
	
	public KgoCasesetTask findByIdCaseSetId(String caseSetId);

	void deleteByIdCaseSetId(String casesetId);

}
