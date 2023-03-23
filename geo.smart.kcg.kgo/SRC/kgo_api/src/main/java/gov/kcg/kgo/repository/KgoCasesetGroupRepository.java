package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoCasesetGroup;
import gov.kcg.kgo.model.KgoCasesetGroupPK;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoCasesetGroupRepositoryCustom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KgoCasesetGroupRepository
		extends BaseRepository<KgoCasesetGroup, KgoCasesetGroupPK>, KgoCasesetGroupRepositoryCustom {

	/**
	 * 
	 * @param caseId
	 * @return
	 */
	public List<KgoCasesetGroup> findByIdCaseSetIdOrderByOrderNumAsc(String caseSetId);

	/**
	 * 取得當前 Order 最大值 + 1
	 * 
	 * @param caseSetId
	 * @return
	 */
	@Query(value = "select MAX(OrderNum) + 1 ORDER_NUM from KGO_CASESET_GROUP where CaseSetId = :caseSetId", nativeQuery = true)
	public String getNextOrderValue(@Param("caseSetId") String caseSetId);

	/**
	 * 
	 * @param caseSetId
	 * @return
	 */
	public int deleteByIdCaseSetId(String caseSetId);

	/**
	 * 
	 * @param caseSetId
	 * @param version
	 * @return
	 */
	public List<KgoCasesetGroup> findByIdCaseSetIdAndIdVersionOrderByOrderNumAsc(String caseSetId, Integer version);

	/**
	 *
	 * @param caseSetId
	 * @return
	 */
	public List<KgoCasesetGroup> findByIdCaseSetIdOrderByOrderNumDesc(String caseSetId);

}
