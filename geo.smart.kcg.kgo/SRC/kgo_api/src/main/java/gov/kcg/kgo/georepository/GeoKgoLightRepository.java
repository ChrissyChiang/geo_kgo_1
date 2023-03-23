package gov.kcg.kgo.georepository; 

import org.springframework.data.jpa.repository.JpaRepository; 
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoLight;

import java.util.List;

/** 
 * GEO 20210829 add
 *
 * Repository for 高雄路燈資訊
 */

@Transactional
public interface GeoKgoLightRepository extends JpaRepository<GeoKgoLight, Integer> {

    List<GeoKgoLight> findByLightDistrict(String lightDistrict);

} 
