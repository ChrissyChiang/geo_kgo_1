package gov.kcg.kgo.georepository; 

import gov.kcg.kgo.geoentity.GeoKgoAppointmentContactUserPK;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoAppointmentContactUser;
import java.util.List;

/** 
 * GEO 20211015 add
 * Repository for 線上預約臨櫃承辦人
 */
@Transactional
public interface GeoKgoAppointmentContactUserRepository extends JpaRepository<GeoKgoAppointmentContactUser, GeoKgoAppointmentContactUserPK> {

    List<GeoKgoAppointmentContactUser> findAllByAppointmentMainId(String appointmentMainId);

} 
