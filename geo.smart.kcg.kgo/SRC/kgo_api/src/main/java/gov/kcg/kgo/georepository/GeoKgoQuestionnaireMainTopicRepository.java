package gov.kcg.kgo.georepository; 

import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireMainTopicPK;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireMainTopic; 

/** 
 * GEO 20210829 add
 *
 * Repository for 問卷題組對應
 */

@Transactional
public interface GeoKgoQuestionnaireMainTopicRepository extends JpaRepository<GeoKgoQuestionnaireMainTopic, GeoKgoQuestionnaireMainTopicPK> {

    void deleteByQuestionId(String question);
    Long countByQuestionId(String question);


} 
