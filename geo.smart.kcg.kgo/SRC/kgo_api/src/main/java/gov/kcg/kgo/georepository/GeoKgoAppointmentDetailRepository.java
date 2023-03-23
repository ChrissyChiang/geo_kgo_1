package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/** 
 * GEO 20211015 add
 * Repository for 線上預約臨櫃細節
 */
@Transactional
public interface GeoKgoAppointmentDetailRepository extends JpaRepository<GeoKgoAppointmentDetail, String> {

    GeoKgoAppointmentDetail findByAppointmentDetailId(String appointmentDetailId);
    List<GeoKgoAppointmentDetail> findAllByAppointmentMainId(String appointmentId);
    List<GeoKgoAppointmentDetail> findAllByAppointmentMainIdAndIsEnable(String appointmentId, Integer isEnable);
} 
