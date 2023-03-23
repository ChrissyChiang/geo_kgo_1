package gov.kcg.kgo.georepository; 

import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoAppointmentMain; 

/** 
 * GEO 20211015 add
 * Repository for 線上預約臨櫃主檔
 */
@Transactional
public interface GeoKgoAppointmentMainRepository extends JpaRepository<GeoKgoAppointmentMain, String> {

    GeoKgoAppointmentMain findByAppointmentMainId(String appointmentMainId);

} 
