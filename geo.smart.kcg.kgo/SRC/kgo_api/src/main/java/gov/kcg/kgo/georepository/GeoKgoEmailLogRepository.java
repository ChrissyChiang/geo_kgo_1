package gov.kcg.kgo.georepository;


import gov.kcg.kgo.geoentity.GeoKgoEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * GEO 20220510 add 案件狀態更新修改
 * Repository for 案件狀態更新紀錄email
 */
@Transactional
public interface GeoKgoEmailLogRepository  extends JpaRepository<GeoKgoEmailLog,String> {

    Boolean existsByCaseIdAndStatuteLike(String caseId,String statute);
}
