package gov.kcg.kgo.georepository; 

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import gov.kcg.kgo.geoentity.GeoKgoHcaOrgan; 

/** 
 * 健保卡所屬機關
 */

public interface GeoKgoHcaOrganRepository extends JpaRepository<GeoKgoHcaOrgan, Long> {

	List<GeoKgoHcaOrgan> findByHcaCardNumber(String cardNumber);

} 