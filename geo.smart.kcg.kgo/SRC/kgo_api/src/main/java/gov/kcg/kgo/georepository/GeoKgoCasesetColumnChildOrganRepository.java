package gov.kcg.kgo.georepository; 

import gov.kcg.kgo.geoentity.GeoKgoCasesetColumnChildOrganPK;
import gov.kcg.kgo.model.KgoCasesetColumnChild;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoCasesetColumnChildOrgan;

import java.util.List;

/** 
 * GEO 20211109 add 機關審核表單
 * Repository for 機關審核表單 服務設定子欄位
 */
@Transactional
public interface GeoKgoCasesetColumnChildOrganRepository extends JpaRepository<GeoKgoCasesetColumnChildOrgan, GeoKgoCasesetColumnChildOrganPK> {

    List<GeoKgoCasesetColumnChildOrgan> findByIdCaseSetIdAndIdVersionAndIdColumnIdAndIdCaseFormVersionOrderByOrderNumAsc(String caseSetId, Integer version, String columnId, Integer caseFormVersion);
    List<GeoKgoCasesetColumnChildOrgan> findByIdCaseSetIdAndIdVersionAndIdCaseFormVersion(String caseSetId, Integer version, Integer caseFormVersion);
    void deleteByIdCaseSetId(String caseSetId);

} 
