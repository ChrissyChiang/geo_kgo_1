package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.geoentity.GeoKgoAppointmentDetail;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentDetailTime;
import gov.kcg.kgo.geoenum.GeoAppointmentMainStatusType;
import gov.kcg.kgo.geomodel.*;
import gov.kcg.kgo.util.DateUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * GEO 20211224 add
 * 取得線上預約臨櫃主檔相關資料
 */

@Repository
public class GeoKgoAppointmentMainReposCustom extends GeoBaseReposCustom {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoKgoAppointmentMainReposCustom.class);

    public Integer findMaxVersionByAppointmentMainId(String appointmentMainId) {
        String sqlStr = "select MAX([Version]) MAX_VERSION ";
        String fromStr = "from GEO_KGO_APPOINTMENT_MAIN ";
        String whereStr = "where appointment_main_id = :appointmentMainId ";
        Query query = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr);
        query.setParameter("appointmentMainId", appointmentMainId);
        Integer maxVerison = null;
        try {
            maxVerison = (Integer) query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("findMaxVersionByAppointmentMainId error:" + e.getMessage());
        }
        return maxVerison;
    } //findMaxVersionByAppointmentMainId

    //找出時間範圍內的已設定的時段
    public List<GeoKgoAppointmentDetailTimeInsertModel> findSetTimeListRangeInfo(String startDate,String endDate,String appointmentDetailId) {
        LOGGER.info("findSetTimeListRangeInfo startDate="+startDate+",endDate="+endDate);
        String sqlStr = "select appointment_detail_time_id, appointment_detail_id ";
        String fromStr = "from GEO_KGO_APPOINTMENT_DETAIL_TIME ";
        String whereStr = "where appointment_detail_id like concat('%', :appointmentDetailId, '%') and start_time>= Convert(datetime, :startDate)  and end_time<= Convert(datetime, :endDate) ; ";
        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr+whereStr);
        if (startDate != null) sqlQuery.setParameter("startDate", startDate);
        if (endDate != null) sqlQuery.setParameter("endDate", endDate);
        if (endDate != null) sqlQuery.setParameter("appointmentDetailId", appointmentDetailId);
        LOGGER.info("findSetTimeListRangeInfo sql="+startDate+endDate+appointmentDetailId);
        List listData = sqlQuery.getResultList();
        List<GeoKgoAppointmentDetailTimeInsertModel> detailTimeList = Collections.synchronizedList(new ArrayList<>());
        if (listData!=null && listData.size()>0){
            for (int i=0;i<listData.size();i++){
                Object[] recordArray = (Object[]) listData.get(i);
                GeoKgoAppointmentDetailTime time = new GeoKgoAppointmentDetailTime();
                time.setAppointmentDetailTimeId((String)recordArray[0]);
                time.setAppointmentDetailId((String)recordArray[1]);
                GeoKgoAppointmentDetailTimeInsertModel model = GeoKgoAppointmentDetailTimeInsertModel.changeToModel(time);
                detailTimeList.add(model);
            } //for (int i=0;i<listData.size();i++)
        } //if (listData!=null && listData.size()>0)
        return detailTimeList;
    } //findMaxVersionByAppointmentMainId
}
