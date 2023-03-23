package gov.kcg.kgo.georepository; 

import org.springframework.data.jpa.repository.JpaRepository; 
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireAnswer;
import java.util.List;

/** 
 * GEO 20210829 add
 *
 * Repository for 問卷作答主檔
 */

@Transactional
public interface GeoKgoQuestionnaireAnswerRepository extends JpaRepository<GeoKgoQuestionnaireAnswer, String> {


    List<GeoKgoQuestionnaireAnswer> findByCaseSetId(String s);
}
