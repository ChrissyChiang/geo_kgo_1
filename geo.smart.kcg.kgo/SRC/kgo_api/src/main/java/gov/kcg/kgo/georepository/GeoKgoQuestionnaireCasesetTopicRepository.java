package gov.kcg.kgo.georepository; 

import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetTopic;
import java.util.List;

/** 
 * GEO 20210829 add
 *
 * Repository for 服務問卷題組檔
 */

@Transactional
public interface GeoKgoQuestionnaireCasesetTopicRepository extends JpaRepository<GeoKgoQuestionnaireCasesetTopic, String> {

    GeoKgoQuestionnaireCasesetTopic findByTopicCasesetId(String topicCaseSetId);
    List<GeoKgoQuestionnaireCasesetTopic> findAllByCaseSetIdAndQuestiinVersion(String caseSetId, Integer version);
    List<GeoKgoQuestionnaireCasesetTopic> findAllByCaseSetId(String caseSetId);
    void deleteByCaseSetId(String caseSetId);

}
