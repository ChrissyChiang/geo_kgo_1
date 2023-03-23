package gov.kcg.kgo.georepository; 

import gov.kcg.kgo.geoentity.GeoKgoCaseDetailOrganPK;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoCaseDetailOrgan;

import java.util.List;

/** 
 * GEO 20211109 add
 * Repository for 機關審核表單 案件資料檔
 */
@Transactional
public interface GeoKgoCaseDetailOrganRepository extends JpaRepository<GeoKgoCaseDetailOrgan, GeoKgoCaseDetailOrganPK> {

    List<GeoKgoCaseDetailOrgan> findByIdCaseId(String caseId);
}
