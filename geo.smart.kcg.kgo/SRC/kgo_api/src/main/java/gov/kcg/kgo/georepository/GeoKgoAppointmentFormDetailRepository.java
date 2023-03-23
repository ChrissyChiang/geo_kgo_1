package gov.kcg.kgo.georepository; 

import gov.kcg.kgo.geoentity.GeoKgoAppointmentFormDetailPK;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoAppointmentFormDetail;

import java.util.List;

/** 
 * GEO 20211224 add 線上預約表單
 * Repository for 預約表單 預約資料檔
 */
@Transactional
public interface GeoKgoAppointmentFormDetailRepository extends JpaRepository<GeoKgoAppointmentFormDetail, GeoKgoAppointmentFormDetailPK> {

    List<GeoKgoAppointmentFormDetail> findByIdAppointmentId(String appointmentId);

} 
