package gov.kcg.kgo.georepository; 

import org.springframework.data.jpa.repository.JpaRepository; 
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireAnswerDetail;

import java.util.List;

/** 
 * GEO 20210829 add
 *
 * Repository for 問卷作答內容
 */

@Transactional
public interface GeoKgoQuestionnaireAnswerDetailRepository extends JpaRepository<GeoKgoQuestionnaireAnswerDetail, String> {
    GeoKgoQuestionnaireAnswerDetail findByAnswerIdAndDetailCasesetId(String answerId, String detailCaseSetId);
    List<GeoKgoQuestionnaireAnswerDetail> findByAnswerId(String answerId);

} 
