package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * GEO 20210829 add
 * <p>
 * Repository for 服務問卷子題目檔
 */

@Transactional
public interface GeoKgoQuestionnaireCasesetDetailRepository extends JpaRepository<GeoKgoQuestionnaireCasesetDetail, String> {

    List<GeoKgoQuestionnaireCasesetDetail> findAllByTopicCasesetId(String topicCaseSetI);

    List<GeoKgoQuestionnaireCasesetDetail> findAllByTopicCasesetIdAndDetailType(String topicCaseSetId, Integer type);

    void deleteByDetailCasesetId(String caseSetId);

    void deleteByTopicCasesetId(String topCaseSetId);
} 
