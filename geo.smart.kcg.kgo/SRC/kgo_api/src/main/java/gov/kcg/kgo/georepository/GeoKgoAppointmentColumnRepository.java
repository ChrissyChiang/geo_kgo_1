package gov.kcg.kgo.georepository; 

import gov.kcg.kgo.geoentity.GeoKgoAppointmentColumnPK;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoAppointmentColumn;

import java.util.List;

/** 
 * GEO 20211224 add 線上預約表單
 * Repository for 預約表單 主欄位
 */
@Transactional
public interface GeoKgoAppointmentColumnRepository extends JpaRepository<GeoKgoAppointmentColumn, GeoKgoAppointmentColumnPK> {

    List<GeoKgoAppointmentColumn> findByIdAppointmentMainIdAndGroupSeqOrderByOrderNumAsc(String appointmentMainId, Integer groupSeq);

    List<GeoKgoAppointmentColumn> findByIdAppointmentMainIdAndGroupSeqAndIdVersionOrderByOrderNumAsc(String appointmentMainId, Integer groupSeq,Integer version);

    List<GeoKgoAppointmentColumn> findByIdAppointmentMainIdAndIdVersionOrderByOrderNumAsc(String appointmentMainId,Integer version);

} 
