package gov.kcg.kgo.georepository; 

import gov.kcg.kgo.geoentity.GeoKgoAppointmentGroupPK;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoAppointmentGroup; 

/** 
 * GEO 20211224 add 線上預約表單
 * Repository for 預約表單 設定群組
 */
@Transactional
public interface GeoKgoAppointmentGroupRepository extends JpaRepository<GeoKgoAppointmentGroup, GeoKgoAppointmentGroupPK> {



} 
