package gov.kcg.kgo.georepository; 

import gov.kcg.kgo.geoentity.GeoKgoAppointmentColumn;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentColumnChildPK;
import gov.kcg.kgo.geoentity.GeoKgoCasesetColumnChildOrgan;
import gov.kcg.kgo.model.KgoCasesetColumnChild;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoAppointmentColumnChild;

import java.util.List;

/** 
 * GEO 20211224 add 線上預約表單
 * Repository for 預約表單子欄位
 */
@Transactional
public interface GeoKgoAppointmentColumnChildRepository extends JpaRepository<GeoKgoAppointmentColumnChild, GeoKgoAppointmentColumnChildPK> {

    List<GeoKgoAppointmentColumnChild> findByIdAppointmentMainIdAndIdVersionAndIdColumnIdOrderByIdOrderNumAsc(String appointmentMainId, Integer version, String columnId);

    List<GeoKgoAppointmentColumnChild> findByIdAppointmentMainIdAndIdVersion(String caseSetId, Integer version);

} 
