package gov.kcg.kgo.georepository;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentCheck;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentCheckPK;
import gov.kcg.kgo.model.KgoCasesetCheck;
import gov.kcg.kgo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 20220811 GEO add
 * 後台-線上預約臨櫃-編輯：身分驗證初始畫面
 */
@Transactional
public interface GeoKgoAppointmentCheckRepository extends BaseRepository<GeoKgoAppointmentCheck, GeoKgoAppointmentCheckPK> {
    /**
     *
     * @param appointmentMainId
     * @return
     */
    public List<GeoKgoAppointmentCheck> findAllByIdAppointmentMainId(String appointmentMainId);

    /**
     *
     * @param appointmentMainId
     * @return
     */
    public void deleteByIdAppointmentMainId(String appointmentMainId);
}
