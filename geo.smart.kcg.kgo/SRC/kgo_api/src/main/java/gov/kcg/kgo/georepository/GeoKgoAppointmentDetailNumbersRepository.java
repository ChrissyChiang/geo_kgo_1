package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentDetailNumbers;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/** 
 * GEO 20211015 add
 * Repository for 線上預約臨櫃細節-號碼牌
 */
@Transactional
public interface GeoKgoAppointmentDetailNumbersRepository extends JpaRepository<GeoKgoAppointmentDetailNumbers, String> {

    GeoKgoAppointmentDetailNumbers findByAppointmentDetailNumbersId(String appointmentDetailNumbersId);

    List<GeoKgoAppointmentDetailNumbers> findAllByAppointmentDetailTimeId(String appointmentDetailTimeId);

    List<GeoKgoAppointmentDetailNumbers> findAllByAppointmentDetailTimeIdAndIsUsed(String appointmentDetailTimeId, Integer isUsed);

    void deleteByAppointmentDetailTimeId(String appointmentDetailTimeId);
} 
