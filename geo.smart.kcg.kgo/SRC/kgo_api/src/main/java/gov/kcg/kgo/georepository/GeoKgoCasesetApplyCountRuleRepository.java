package gov.kcg.kgo.georepository; 

import org.springframework.data.jpa.repository.JpaRepository; 
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoCasesetApplyCountRule; 

/** 
 * GEO 20211123 add 服務申辦統計名次排序條件
 * Repository for 服務申辦統計名次排序條件
 */
@Transactional
public interface GeoKgoCasesetApplyCountRuleRepository extends JpaRepository<GeoKgoCasesetApplyCountRule, Integer> {



} 
