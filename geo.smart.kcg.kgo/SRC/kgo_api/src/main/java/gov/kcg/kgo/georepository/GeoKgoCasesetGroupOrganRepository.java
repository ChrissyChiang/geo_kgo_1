package gov.kcg.kgo.georepository; 

import gov.kcg.kgo.geoentity.GeoKgoCasesetGroupOrganPK;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoCasesetGroupOrgan;
import java.util.List;

/** 
 * GEO 20211109 add 機關審核表單
 * Repository for 機關審核表單 案件設定群組
 */
@Transactional
public interface GeoKgoCasesetGroupOrganRepository extends JpaRepository<GeoKgoCasesetGroupOrgan, GeoKgoCasesetGroupOrganPK> {

    List<GeoKgoCasesetGroupOrgan> findByIdCaseSetIdAndIdVersionAndIdCaseFormVersionOrderByOrderNum(String caseSetId, Integer version, Integer caseFormVersion);

    void deleteByIdCaseSetId(String casetId);
}
