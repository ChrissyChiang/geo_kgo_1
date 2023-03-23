package gov.kcg.kgo.georepository; 

import org.springframework.data.jpa.repository.JpaRepository; 
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireTopicDetail; 

/** 
 * GEO 20210829 add
 *
 * Repository for 問卷題組子題目檔
 */

@Transactional
public interface GeoKgoQuestionnaireTopicDetailRepository extends JpaRepository<GeoKgoQuestionnaireTopicDetail, String> {

    GeoKgoQuestionnaireTopicDetail findByDetailId(String detailId);
    void deleteByDetailId(String detailId);
} 
