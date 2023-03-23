package gov.kcg.kgo.georepository; 

import org.springframework.data.jpa.repository.JpaRepository; 
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoCaseCrossReviewComment;

import java.util.List;

/** 
 * GEO 20211115 add 跨機關檢視 備註
 * Repository for 跨機關檢視 備註
 */
@Transactional
public interface GeoKgoCaseCrossReviewCommentRepository extends JpaRepository<GeoKgoCaseCrossReviewComment, String> {

    List<GeoKgoCaseCrossReviewComment> findByCaseIdAndUserId(String caseId, String userId);
}
