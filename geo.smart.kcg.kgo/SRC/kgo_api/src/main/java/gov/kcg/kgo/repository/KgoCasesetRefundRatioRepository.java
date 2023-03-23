package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoCasesetRefundRatio;
import gov.kcg.kgo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface KgoCasesetRefundRatioRepository extends BaseRepository<KgoCasesetRefundRatio, Long> {

	int deleteByCasesetId(String caseSetId);
	List<KgoCasesetRefundRatio> findByCasesetId(String casesetId);
	KgoCasesetRefundRatio findByCasesetIdAndFromDaysAndEndDays(String casesetId , Integer fromDays ,Integer endDays);
	@Query(value="SELECT refund_ratio  FROM GEO_KGO_CASESET_REFUND_RATIO  WHERE case_set_id = :casesetId  AND :remaining >from_days AND :remaining < end_days ",nativeQuery = true)
	Integer findRefundPercent(@Param("casesetId")String casesetId ,@Param("remaining")Integer remaining);
	void deleteAllByCasesetId(String casesetId);
}
