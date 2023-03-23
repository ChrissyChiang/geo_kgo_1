package gov.kcg.kgo.georepository; 

import org.springframework.data.jpa.repository.JpaRepository; 
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoTaskComment; 

/** 
 * GEO 20211101 add
 * Repository for 簽核意見
 */
@Transactional
public interface GeoKgoTaskCommentRepository extends JpaRepository<GeoKgoTaskComment, String> {
    GeoKgoTaskComment findByCommentId(String commentId);
} 
