package gov.kcg.kgo.georepository; 

import org.springframework.data.jpa.repository.JpaRepository; 
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireTopic; 

/** 
 * GEO 20210829 add
 *
 * Repository for 問卷題組檔
 */

@Transactional
public interface GeoKgoQuestionnaireTopicRepository extends JpaRepository<GeoKgoQuestionnaireTopic, String> {

    GeoKgoQuestionnaireTopic findByTopicId(String topicId);

} 
