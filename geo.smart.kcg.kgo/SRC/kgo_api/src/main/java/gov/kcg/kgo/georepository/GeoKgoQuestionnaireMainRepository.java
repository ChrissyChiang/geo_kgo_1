package gov.kcg.kgo.georepository; 

import org.springframework.data.jpa.repository.JpaRepository; 
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireMain; 

/** 
 * GEO 20210829 add
 *
 * Repository for 問卷主檔
 */

@Transactional
public interface GeoKgoQuestionnaireMainRepository extends JpaRepository<GeoKgoQuestionnaireMain, String> {

    GeoKgoQuestionnaireMain findByQuestionId(String questionId);

} 
