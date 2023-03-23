package gov.kcg.kgo.georepository; 

import org.springframework.data.jpa.repository.JpaRepository; 
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoAgent; 

/** 
 * GEO 20211026 add
 * Repository for 代理人機制
 */
@Transactional
public interface GeoKgoAgentRepository extends JpaRepository<GeoKgoAgent, String> {

    GeoKgoAgent findByAgentId(String agentId);

} 
