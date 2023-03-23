package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.model.KgoCasesetGroupLevel;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoCasesetGroupLevelRepository extends BaseRepository<KgoCasesetGroupLevel, Integer> {

	List<KgoCasesetGroupLevel> findByCaseSetIdAndMainType(String caseSetId, String mainType);

	Integer deleteByCaseSetIdAndMainType(String caseSetId, String mainType);

	@Query(value = "select a from KgoCasesetGroupLevel a INNER join KgoCaseset b on a.caseSetId = b.caseSetId and b.status='On' where groupLevelSeq = :groupLevelSeq")
	List<KgoCasesetGroupLevel> findAllByGroupLevelSeq(@Param("groupLevelSeq") String groupLevelSeq);

}
