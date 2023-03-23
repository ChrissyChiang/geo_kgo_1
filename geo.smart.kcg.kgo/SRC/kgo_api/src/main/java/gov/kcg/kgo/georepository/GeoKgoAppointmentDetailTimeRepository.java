package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentDetailTime;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * GEO 20211015 add
 * Repository for 線上預約臨櫃細節-預約時段
 */
@Transactional
public interface GeoKgoAppointmentDetailTimeRepository extends JpaRepository<GeoKgoAppointmentDetailTime, String> {

    GeoKgoAppointmentDetailTime findByAppointmentDetailTimeId(String appointmentDetailTimeId);
    List<GeoKgoAppointmentDetailTime> findAllByAppointmentDetailId(String appointmentDetailId);
    List<GeoKgoAppointmentDetailTime> findByAppointmentDetailId(String detailId);
    List<GeoKgoAppointmentDetailTime> findAllBy();
    void deleteByAppointmentDetailTimeId(String appointmentDetailTimeId);
} 
