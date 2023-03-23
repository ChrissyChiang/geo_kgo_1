package gov.kcg.kgo.georepository; 

import gov.kcg.kgo.geoentity.GeoKgoCasesetCrossReviewPK;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoCasesetCrossReview;

import java.util.List;

/** 
 * GEO 20211115 add 跨機關檢視
 * Repository for 跨機關檢視權限 機關服務對應
 */
@Transactional
public interface GeoKgoCasesetCrossReviewRepository extends JpaRepository<GeoKgoCasesetCrossReview, GeoKgoCasesetCrossReviewPK> {
    Integer deleteAllByIdCaseSetId(String caseSetId);
    List<GeoKgoCasesetCrossReview> findByIdCaseSetId(String caseSetId);
} 
