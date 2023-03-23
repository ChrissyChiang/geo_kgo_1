package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * GEO 20211015 add
 * Repository for 線上預約臨櫃-登錄預約主檔
 */
@Transactional
public interface GeoKgoAppointmentRepository extends JpaRepository<GeoKgoAppointment, String> {

    GeoKgoAppointment findByAppointmentId(String appointmentId);

    Boolean existsGeoKgoAppointmentByAppointmentMainId(String appointmentMainId);

    Boolean existsGeoKgoAppointmentByAppointmentDetailTimeId(String appointmentDetailTimeId);

    GeoKgoAppointment findByAppointmentDetailTimeIdAndAppointmentIdentity(String appointmentDetailTimeId,String appointmentIdentity);

    Boolean existsByAppointmentDetailNumbersId(String appointmentDetailNumbersId);
}
