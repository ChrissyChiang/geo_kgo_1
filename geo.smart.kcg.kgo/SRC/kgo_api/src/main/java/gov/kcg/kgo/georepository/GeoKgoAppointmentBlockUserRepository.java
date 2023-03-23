package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentBlockUser;
import gov.kcg.kgo.model.KgoCaseMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/** 
 * GEO 20211015 add
 * Repository for 線上預約臨櫃名稱黑名單
 */
@Transactional
public interface GeoKgoAppointmentBlockUserRepository extends JpaRepository<GeoKgoAppointmentBlockUser, String> {

    GeoKgoAppointmentBlockUser findByAppointmentMainIdAndAndBlockUseNameAndAndBlockUserIdentity(String appointmentMainId, String blockUserName, String blockUserIdentity);
    GeoKgoAppointmentBlockUser findByBlockUserId(String blockUserId);
//    GeoKgoAppointmentBlockUser findByAppointmentMainIdAndBlockUserId(String appointmentMainId,String blockUserId);
    List<GeoKgoAppointmentBlockUser> findAllByAppointmentMainIdAndIsTriggerBlock(String appointmentMainId, Integer isTriggerBlock);

    /**
     * 依據appointmentMainId, blockUserIdentity查找.
     *
     */
    @Query(value = "select * " + "from GEO_KGO_APPOINTMENT_BLOCK_USER "  +  "where appointment_main_id = :appointmentMainId and block_user_identity = :identity ", nativeQuery = true)
    GeoKgoAppointmentBlockUser findByAppointmentMainIdAndIdentity(String appointmentMainId, String identity);

} 
