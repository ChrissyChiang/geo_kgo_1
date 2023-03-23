package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoCasesetColumnOrgan;
import gov.kcg.kgo.geoentity.GeoKgoCasesetColumnOrganPK;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * GEO 20211109 add 機關審核表單
 * Repository for 機關審核表單 服務設定欄位
 */
@Transactional
public interface GeoKgoCasesetColumnOrganRepository extends JpaRepository<GeoKgoCasesetColumnOrgan, GeoKgoCasesetColumnOrganPK> {
    public List<GeoKgoCasesetColumnOrgan> findByIdCaseSetIdAndGroupSeqOrderByOrderNumAsc(String caseSetId, Integer groupSeq);
    List<GeoKgoCasesetColumnOrgan> findByIdCaseSetIdAndGroupSeqAndIdCaseFormVersion(String caseSetId, Integer groupSeq, Integer version);
    void deleteByIdCaseSetId(String caseSetId);

} 
