package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.geoenum.CaseSetCategoryEnum;
import gov.kcg.kgo.geomodel.dto.GeoAppointmentGroupQueryDataMaxVersionDto;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentDetail;
import gov.kcg.kgo.geoenum.GeoAppointmentMainStatusType;
import gov.kcg.kgo.geomodel.*;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.SpringUtil;

import org.activiti.engine.TaskService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static gov.kcg.kgo.util.KgoUtil.DEFAULT_VERSION_NUMBER;

/**
 * GEO 20211015 add
 * 取得線上預約臨櫃相關資料
 */

@Repository
public class GeoKgoAppointmentReposCustom extends GeoBaseReposCustom {
	@Autowired
	private TaskService taskService;	

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoKgoAppointmentReposCustom.class);

    @PersistenceContext
    public EntityManager entityManager;

    /**
     * 後台-線上預約臨櫃:承辦人帳號搜尋
     *
     * @param organId 機關代碼
     * @param unitId  單位代碼
     * @param name    使用者姓名
     */
    public List<GeoKgoAppointmentContactUserModel> findAccountQueryData(String organId, String unitId, String name) {

        StringBuilder sb = new StringBuilder();

        /**
         * Generate custom query String
         */

        sb.append("select o.OrganName as ORGAN_NAME, ut.UnitName as UNIT_NAME, u.UserId as USER_ID, u.Name as NAME, " +
                "u.Organ as ORGAN_ID, u.Unit as UNIT_ID ");
        sb.append("from KGO_USER u ");
        sb.append("left join KGO_USER_ROLE r on u.UserId = r.UserId ");
        sb.append("left join KGO_Organ o on u.Organ = o.OrganId ");
        sb.append("left join KGO_UNIT ut on u.Organ = ut.OrganId and u.Unit = ut.UnitId ");
        sb.append("where 1=1 ");
        if (StringUtils.isNotBlank(organId)) {
            sb.append("and u.Organ = :organId ");
        }
        if (StringUtils.isNotBlank(unitId)) {
            sb.append("and u.Unit = :unitId ");
        }
        if (StringUtils.isNotBlank(name)) {
            sb.append("and u.Name like CONCAT('%', :name,'%') ");
        }

        sb.append("group by o.OrganName, ut.UnitName, u.UserId, u.Name, u.Organ, u.Unit ");

        Query query = entityManager.createNativeQuery(sb.toString());
        if (ObjectUtils.isNotEmpty(organId)) {
            query.setParameter("organId", organId);
        }
        if (ObjectUtils.isNotEmpty(unitId)) {
            query.setParameter("unitId", unitId);
        }
        if (StringUtils.isNotBlank(name)) {
            query.setParameter("name", name);
        }

        List listData = query.getResultList();
        List<GeoKgoAppointmentContactUserModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoKgoAppointmentContactUserModel model = new GeoKgoAppointmentContactUserModel();
            Object[] recordArray = (Object[]) listData.get(i);
            model.setOrganName((String) recordArray[0]);
            model.setUnitName((String) recordArray[1]);
            model.setUserId((String) recordArray[2]);
            model.setName((String) recordArray[3]);
            model.setOrganId((String) recordArray[4]);
            model.setUnitId((String) recordArray[5]);
            datas.add(model);
        } // for (int i=0; i<listData.size(); i++)
        return datas;
    } //findAccountQueryData

    /**
     * 後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單
     *
     * @param status
     * @param organId
     * @param unitId
     * @param appointmentName
     * @return
     */
    public List<GeoKgoAppointmentMainQueryModel> getAppointMainListByKeyword(String status, String organId, String unitId, String appointmentName) {
        return getAppointMainList(status, organId, unitId, appointmentName, null);
    }

    /**
     * 後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單
     */
    public GeoKgoAppointmentMainQueryModel getAppointMainListById(String status, String appointmentId) {
        List<GeoKgoAppointmentMainQueryModel> result = getAppointMainList(status, null, null, null, appointmentId);
        if (result != null && result.size() > 0) return result.get(0);
        return null;
    }

    /**
     * 後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單
     */
    private List<GeoKgoAppointmentMainQueryModel> getAppointMainList(String status, String organId, String unitId, String appointmentName, String appointmentId) {
        String sqlStr = "SELECT am.organ_id, am.unit_id,o.OrganName, ut.UnitName, am.appointment_name, am.appointment_main_id, am.appointment_status, am.mydata_url, am.edit_time, am.is_service_html, am.service_html ";
        String fromStr = "FROM GEO_KGO_APPOINTMENT_MAIN am left join KGO_Organ o on am.organ_id = o.OrganId " +
                "left join KGO_UNIT ut on am.organ_id = ut.OrganId and am.unit_id = ut.UnitId ";
        String whereStr = "where 1=1 ";
        if (StringUtils.isNotBlank(status)) {
            if (status.equals(GeoAppointmentMainStatusType.DELETE.getLabel()))
                whereStr += "and am.appointment_status != :status ";
            else whereStr += "and am.appointment_status = :status ";
        }
        if (StringUtils.isNotBlank(organId)) whereStr += "and am.organ_id = :organId ";
        if (StringUtils.isNotBlank(unitId)) whereStr += "and am.unit_id = :unitId ";
        if (StringUtils.isNotBlank(appointmentName))
            whereStr += "and am.appointment_name like CONCAT('%', :appointmentName,'%') ";
        if (StringUtils.isNotBlank(appointmentId)) whereStr += "and am.appointment_main_id = :appointmentId ";

        Query query = entityManager.createNativeQuery(sqlStr + fromStr + whereStr);
        if (StringUtils.isNotEmpty(status)) query.setParameter("status", status);
        if (StringUtils.isNotEmpty(organId)) query.setParameter("organId", organId);
        if (StringUtils.isNotEmpty(unitId)) query.setParameter("unitId", unitId);
        if (StringUtils.isNotBlank(appointmentName)) query.setParameter("appointmentName", appointmentName);
        if (StringUtils.isNotBlank(appointmentId)) query.setParameter("appointmentId", appointmentId);

        LOGGER.info("getAppointMainList sql="+sqlStr + fromStr + whereStr);
        LOGGER.info("getAppointMainList status="+status + ",organId" + organId+",unitId="+unitId+",appointmentName="+appointmentName+",appointmentId="+appointmentId);

        List listData = query.getResultList();
        List<GeoKgoAppointmentMainQueryModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoKgoAppointmentMainQueryModel model = new GeoKgoAppointmentMainQueryModel();
            Object[] recordArray = (Object[]) listData.get(i);
            model.setOrganId((String) recordArray[0]);
            model.setUnitId((String) recordArray[1]);
            model.setOrganName((String) recordArray[2]);
            model.setUnitName((String) recordArray[3]);
            model.setAppointmentName((String) recordArray[4]);
            model.setAppointmentMainId((String) recordArray[5]);
            model.setAppointmentStatus((String) recordArray[6]);
            model.setMydataUrl((String) recordArray[7]);
            model.setEditTime((Timestamp) recordArray[8]);

            /** 20220811 GEO add */
            model.setServiceHtml(false);
            if (recordArray[9]!=null && (int)recordArray[9] !=0) model.setServiceHtml(true);
            model.setServiceHtmlContent("");
            if (recordArray[10]!=null)model.setServiceHtmlContent((String) recordArray[10]);

            datas.add(model);
        } // for (int i=0; i<listData.size(); i++)
        return datas;
    } //getAppointMainListByKeyword

    /**
     * 後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單
     */
    public List<GeoKgoAppointmentOrderQueryModel> getAppointList(String appointmentMainId, String dateStart, String dateEnd, Integer isAvailable) throws Exception {
        String sqlStr = "SELECT a.appointment_id, a.appointment_main_id, a.appointment_detail_time_id, a.appointment_detail_numbers_id, " +
                "a.appointment_identity, a.appointment_name, a.appointment_email, a.appointment_phone, adt.start_time, adt.end_time, adn.number_name, a.is_online ";
        String fromStr = "FROM GEO_KGO_APPOINTMENT a " +
                " left join GEO_KGO_APPOINTMENT_DETAIL_TIME adt on a.appointment_detail_time_id = adt.appointment_detail_time_id " +
                "left join GEO_KGO_APPOINTMENT_DETAIL_NUMBERS adn on a.appointment_detail_numbers_id = adn.appointment_detail_numbers_id ";
        String whereStr = "where a.appointment_main_id = :appointmentMainId ";
        if (StringUtils.isNotBlank(dateStart)) whereStr += "and adt.start_time >= Convert(datetime, :dateStart) ";
        if (StringUtils.isNotBlank(dateEnd)) whereStr += "and adt.end_time <= Convert(datetime, :dateEnd) ";
        if (isAvailable != null) whereStr += "and a.is_available = :isAvailable ";

        Query query = entityManager.createNativeQuery(sqlStr + fromStr + whereStr);
        query.setParameter("appointmentMainId", appointmentMainId);
        if (StringUtils.isNotBlank(dateStart)) query.setParameter("dateStart", dateStart);
        if (StringUtils.isNotBlank(dateEnd))
            query.setParameter("dateEnd", DateUtil.getEndOfDay(dateEnd, DateUtil.PATTEN_YEAR_MONTH_DAY, DateUtil.PATTEN_FULL_TIME));
        if (isAvailable != null) query.setParameter("isAvailable", isAvailable);

        List listData = query.getResultList();
        List<GeoKgoAppointmentOrderQueryModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoKgoAppointmentOrderQueryModel model = new GeoKgoAppointmentOrderQueryModel();
            Object[] recordArray = (Object[]) listData.get(i);
            model.setAppointmentId((String) recordArray[0]);
            model.setAppointmentMainId((String) recordArray[1]);
            model.setAppointmentDetailTimeId((String) recordArray[2]);
            model.setAppointmentDetailNumbersId((String) recordArray[3]);
            model.setAppointmentIdentity((String) recordArray[4]);
            model.setAppointmentName((String) recordArray[5]);
            model.setAppointmentEmail((String) recordArray[6]);
            model.setAppointmentPhone((String) recordArray[7]);
            model.setDate((Timestamp) recordArray[8]);
            model.setDateStart((Timestamp) recordArray[8]);
            model.setDateEnd((Timestamp) recordArray[9]);
            model.setNumbersName((String) recordArray[10]);
            model.setIsOnline((Integer) recordArray[11]);
            datas.add(model);
        } // for (int i=0; i<listData.size(); i++)
        return datas;
    } //getAppointMainListByKeyword

    /**
     * 前台-線上預約臨櫃:取得該預約單該月預約名額與資訊
     */
    public List<GeoKgoAppointmentNumbersSearchModel> searchAppointmentNumbers(String appointmentMainId, Date firstMonthDay, Date lastMonthDay) throws Exception {
        LOGGER.info("searchAppointmentNumbers firstMonthDay ="+firstMonthDay+",lastMonthDay="+lastMonthDay);

        String sqlStr = "SELECT am.appointment_main_id, ad.appointment_detail_id, ad.appointment_detail_date, SUM(adn.is_used) as availableNumbers ";
        String fromStr = "FROM GEO_KGO_APPOINTMENT_MAIN am " +
                "left join GEO_KGO_APPOINTMENT_DETAIL ad on ad.appointment_main_id = am.appointment_main_id " +
                " left join GEO_KGO_APPOINTMENT_DETAIL_TIME adt on ad.appointment_detail_id = adt.appointment_detail_id " +
                "left join GEO_KGO_APPOINTMENT_DETAIL_NUMBERS adn on adt.appointment_detail_time_id = adn.appointment_detail_time_id ";
        String whereStr = "WHERE ad.is_enable = 1 ";
        if (StringUtils.isNotBlank(appointmentMainId)) whereStr += "and am.appointment_main_id = :appointmentMainId ";
        if (ObjectUtils.isNotEmpty(firstMonthDay) && ObjectUtils.isNotEmpty(lastMonthDay))
            whereStr += "and adt.start_time >= Convert(datetime, :firstMonthDay) and adt.start_time <= Convert(datetime, :lastMonthDay)";
        String orderStr = "GROUP BY am.appointment_main_id, ad.appointment_detail_id,am.appointment_name, ad.appointment_detail_date ";

        Query query = entityManager.createNativeQuery(sqlStr + fromStr + whereStr + orderStr);
        if (StringUtils.isNotBlank(appointmentMainId)) query.setParameter("appointmentMainId", appointmentMainId);
        if (ObjectUtils.isNotEmpty(firstMonthDay) && ObjectUtils.isNotEmpty(lastMonthDay)) {
            query.setParameter("firstMonthDay", firstMonthDay);
            query.setParameter("lastMonthDay", lastMonthDay);
        }

        List listData = query.getResultList();
        List<GeoKgoAppointmentNumbersSearchModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoKgoAppointmentNumbersSearchModel model = new GeoKgoAppointmentNumbersSearchModel();
            Object[] recordArray = (Object[]) listData.get(i);
            model.setAppointmentMainId((String) recordArray[0]);
            model.setAppointmentDetailId((String) recordArray[1]);
            model.setAppointmentDetailDate(DateUtil.dateToString((Timestamp) recordArray[2], DateUtil.PATTEN_YEAR_MONTH_DAY));
            model.setAvailableNumbers((Integer) recordArray[3]);
            datas.add(model);
        } // for (int i=0; i<listData.size(); i++)
        return datas;
    } //searchAppointmentNumbers

    /**
     * 前台-線上預約臨櫃:取得單日預約資料
     */
    public List<GeoKgoAppointmentInfoQueryByDayModel> queryAppointmentInfoByDay(String appointmentDetailId) {
        String sqlStr = "SELECT ad.appointment_main_id, adt.appointment_detail_time_id, ad.location, ad.appointment_detail_date, ad.earliest_day,  " +
                "ad.earliest_time, ad.latest_day, ad.latest_time, adt.start_time, adt.end_time, SUM(adn.is_used) as availableNumbers ";
        String fromStr = "FROM GEO_KGO_APPOINTMENT_DETAIL ad " +
                "left join GEO_KGO_APPOINTMENT_DETAIL_TIME adt on ad.appointment_detail_id = adt.appointment_detail_id " +
                "left join GEO_KGO_APPOINTMENT_DETAIL_NUMBERS adn on adt.appointment_detail_time_id = adn.appointment_detail_time_id ";
        String whereStr = "WHERE 1 = 1 ";
        if (StringUtils.isNotBlank(appointmentDetailId))
            whereStr += "and ad.appointment_detail_id = :appointmentDetailId ";
        String orderStr = "GROUP BY ad.appointment_main_id, adt.appointment_detail_time_id, ad.location, ad.earliest_day, " +
                "ad.earliest_time, ad.latest_day, ad.latest_time, adt.start_time, adt.end_time, ad.appointment_detail_date ";

        Query query = entityManager.createNativeQuery(sqlStr + fromStr + whereStr + orderStr);
        if (StringUtils.isNotBlank(appointmentDetailId)) query.setParameter("appointmentDetailId", appointmentDetailId);

        List listData = query.getResultList();
        List<GeoKgoAppointmentInfoQueryByDayModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoKgoAppointmentInfoQueryByDayModel model = new GeoKgoAppointmentInfoQueryByDayModel();
            Object[] recordArray = (Object[]) listData.get(i);
            model.setAppointmentMainId((String) recordArray[0]);
            model.setAppointmentDetailTimeId((String) recordArray[1]);
            model.setLocation((String) recordArray[2]);
            model.setAppointmentDetailDate((Timestamp) recordArray[3]);
            model.setEarliestTime((Timestamp) recordArray[3], (Integer) recordArray[4], (String) recordArray[5]);
            model.setLatestTime((Timestamp) recordArray[3], (Integer) recordArray[6], (String) recordArray[7]);
            model.setStartTime((Timestamp) recordArray[8]);
            model.setEndTime((Timestamp) recordArray[9]);
            model.setAvailableNumbers((Integer) recordArray[10]);
            datas.add(model);
        } // for (int i=0; i<listData.size(); i++)
        return datas;
    } //queryAppointmentInfoByDay

    /**
     * 前台-線上預約臨櫃:搜尋該民眾已登錄預約單
     */
    public List<GeoKgoAppointmentOrderQueryModel> queryAppointmentListByPerson(String appointmentName, String appointmentIdentity, String appointmentEmail, String appointmentPhone, Timestamp now, List<String> casesetCategory) throws Exception {
    	
    	List<GeoKgoAppointmentOrderQueryModel> datas = Collections.synchronizedList(new ArrayList<>());
    	if(casesetCategory.contains("counter")) { //臨櫃預約
	        String sqlStr = "SELECT a.appointment_id, a.appointment_main_id, a.appointment_detail_time_id, a.appointment_detail_numbers_id, " +
	                "a.appointment_identity, a.appointment_name, a.appointment_email, a.appointment_phone, adt.start_time, adt.end_time, adn.number_name, a.is_available ";
	        String fromStr = "FROM GEO_KGO_APPOINTMENT a " +
	                " left join GEO_KGO_APPOINTMENT_DETAIL_TIME adt on a.appointment_detail_time_id = adt.appointment_detail_time_id " +
	                "left join GEO_KGO_APPOINTMENT_DETAIL_NUMBERS adn on a.appointment_detail_numbers_id = adn.appointment_detail_numbers_id ";
	        String whereStr = "WHERE a.is_available = 1 ";
	        
	        
	        if (StringUtils.isNotBlank(appointmentName)) whereStr += "and a.appointment_name = :appointmentName ";
	        if (StringUtils.isNotBlank(appointmentEmail)) whereStr += "and a.appointment_email = :appointmentEmail ";
	        if (StringUtils.isNotBlank(appointmentIdentity))
	            whereStr += "and a.appointment_identity = :appointmentIdentity ";
	        if (StringUtils.isNotBlank(appointmentPhone)) whereStr += "and a.appointment_phone = :appointmentPhone ";
	        if (ObjectUtils.isNotEmpty(now)) whereStr += "and adt.start_time >= Convert(datetime, :now) ";
	
	        Query query = entityManager.createNativeQuery(sqlStr + fromStr + whereStr);
	        if (StringUtils.isNotBlank(appointmentName)) query.setParameter("appointmentName", appointmentName);
	        if (StringUtils.isNotBlank(appointmentEmail)) query.setParameter("appointmentEmail", appointmentEmail);
	        if (StringUtils.isNotBlank(appointmentIdentity)) query.setParameter("appointmentIdentity", appointmentIdentity);
	        if (StringUtils.isNotBlank(appointmentPhone)) query.setParameter("appointmentPhone", appointmentPhone);
	        if (ObjectUtils.isNotEmpty(now)) query.setParameter("now", now);
	
	        List listData = query.getResultList();
	        
	        for (int i = 0; i < listData.size(); i++) {
	            GeoKgoAppointmentOrderQueryModel model = new GeoKgoAppointmentOrderQueryModel();
	            Object[] recordArray = (Object[]) listData.get(i);
	            model.setAppointmentId((String) recordArray[0]);
	            model.setAppointmentMainId((String) recordArray[1]);
	            model.setAppointmentDetailTimeId((String) recordArray[2]);
	            model.setAppointmentDetailNumbersId((String) recordArray[3]);
	            model.setAppointmentIdentity((String) recordArray[4]);
	            model.setAppointmentName((String) recordArray[5]);
	            model.setAppointmentEmail((String) recordArray[6]);
	            model.setAppointmentPhone((String) recordArray[7]);
	            model.setDate((Timestamp) recordArray[8]);
	            model.setDateStart((Timestamp) recordArray[8]);
	            model.setDateEnd((Timestamp) recordArray[9]);
	            model.setNumbersName((String) recordArray[10]);
	            model.setIsOnline((Integer) recordArray[11]);
	            datas.add(model);
	        } // for (int i=0; i<listData.size(); i++)
    	}
        return datas;
    }     


    /**
     * 後台-線上預約臨櫃-編輯:依時間範圍取得該預約單資料
     */
    public List<GeoKgoAppointmentDetail> getAppointmentInfoByTime(String appointmentMainId, String dateStart, String dateEnd) throws Exception {
        String sqlStr = "SELECT * ";
        String fromStr = "FROM GEO_KGO_APPOINTMENT_DETAIL ";
        String whereStr = "where appointment_main_id = :appointmentMainId ";
        if (StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd))
            whereStr += "AND CONVERT(date, appointment_detail_date) BETWEEN :dateStart AND :dateEnd ";

        Query query = entityManager.createNativeQuery(sqlStr + fromStr + whereStr, GeoKgoAppointmentDetail.class);
        query.setParameter("appointmentMainId", appointmentMainId);
        if (StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)) {
            query.setParameter("dateStart", dateStart);
            query.setParameter("dateEnd", dateEnd);
        }
        return query.getResultList();
    } //getAppointMainListByKeyword


    /**
     * 找尋該預約單底下所有版本最大號的群組資料
     *
     * @param appointmentMainId
     * @param memo
     * @return
     */
    public List<GeoAppointmentGroupQueryDataMaxVersionDto> findMaxVersionGroupData(String appointmentMainId, String memo) {
        Integer maxVersion = findMaxVersionByAppointmentMainId(appointmentMainId);
        if (ObjectUtils.isEmpty(maxVersion)) return null;

        String selectStr = "select GroupSeq GROUP_SEQ, Memo MEMO, Version VERSION, OrderNum ORDER_NUM, IsShow IS_SHOW, CheckFrequencyPeriod CHECK_FREQUENCY_PERIOD ";
        String fromStr = "from GEO_KGO_APPOINTMENT_GROUP ";
        String whereStr = "where Version = :version ";
        String orderStr = "order by GroupSeq ";
        if (StringUtils.isNotBlank(appointmentMainId)) whereStr += "AND AppointmentMainId = :appointmentMainId ";
        if (StringUtils.isNotBlank(memo)) whereStr += "AND Memo = :memo ";

        Query query = entityManager.createNativeQuery(selectStr + fromStr + whereStr + orderStr, GeoAppointmentGroupQueryDataMaxVersionDto.class);
        query.setParameter("version", maxVersion);
        if (StringUtils.isNotBlank(appointmentMainId)) query.setParameter("appointmentMainId", appointmentMainId);
        if (StringUtils.isNotBlank(memo)) query.setParameter("memo", memo);

        return query.getResultList();
    } //findMaxVersionGroupData

    /**
     * 找尋該預約臨櫃底下所有版本最大號
     *
     * @param appointmentMainId
     * @param
     * @return
     */
    public Integer findMaxVersionByAppointmentMainId(String appointmentMainId) {
        String sqlStr = "select MAX([Version]) MAX_VERSION from GEO_KGO_APPOINTMENT_GROUP where AppointmentMainId = :appointmentMainId ";
        Query query = entityManager.createNativeQuery(sqlStr);
        query.setParameter("appointmentMainId", appointmentMainId);
        Integer maxVerison = null;
        try {
            maxVerison = (Integer) query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("GeoKgoAppointmentReposCustom findMaxVersionByAppointmentMainId error:" + e.getMessage());
            return maxVerison;
        }
        return maxVerison;
    } //findMaxVersionByAppointmentMainId

    /**
     * 後台-線上預約臨櫃：新增預約臨櫃表單群組資料
     */
    public Integer saveAppointmentGroupData(String appointmentMainId, int version, String memo, int orderNum, int isShow) {

        StringBuilder sb = new StringBuilder();

        sb.append("insert into GEO_KGO_APPOINTMENT_GROUP ");
        sb.append("(AppointmentMainId, [Version], Memo, OrderNum, IsShow ) ");
        sb.append("values ");
        sb.append("(:appointmentMainId, :version, :memo, :orderNum, :isShow)");

        /**
         * Native Query
         */
        return entityManager.createNativeQuery(sb.toString()).setParameter("appointmentMainId", appointmentMainId)
                .setParameter("version", version).setParameter("memo", memo).setParameter("orderNum", orderNum)
                .setParameter("isShow", isShow)
                .executeUpdate();
    } //saveAppointmentGroupData


    /**
     * 取得下一個預約表單版本號
     *
     */
    public static int getNextVersionValue(String appointmentMainId) {
        GeoKgoAppointmentReposCustom geoKgoAppointmentReposCustom = SpringUtil.getDao(GeoKgoAppointmentReposCustom.class);
        Integer maxVersion = geoKgoAppointmentReposCustom.findMaxVersionByAppointmentMainId(appointmentMainId);
        int nextVersion = ObjectUtils.isEmpty(maxVersion) ? DEFAULT_VERSION_NUMBER : maxVersion.intValue() + 1;
        return nextVersion;
    } //getNextVersionValue
}
