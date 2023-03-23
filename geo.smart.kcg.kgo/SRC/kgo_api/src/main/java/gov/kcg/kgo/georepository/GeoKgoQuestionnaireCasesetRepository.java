package gov.kcg.kgo.georepository; 

import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetPK;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCaseset; 

/** 
 * GEO 20210829 add
 *
 * Repository for 服務問卷主檔
 */

@Transactional
public interface GeoKgoQuestionnaireCasesetRepository extends JpaRepository<GeoKgoQuestionnaireCaseset, GeoKgoQuestionnaireCasesetPK> {

    GeoKgoQuestionnaireCaseset findByCaseSetIdAndQuestiinVersion(String caseSetId, Integer version);
    void deleteByCaseSetId(String caseSetId);
} 
