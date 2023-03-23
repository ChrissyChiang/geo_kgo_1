package gov.kcg.kgo.georepository.custom;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import gov.kcg.kgo.geoentity.GeoKgoCaseSetSiteMain;
import gov.kcg.kgo.geomodel.*;
import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;


@Repository
public class GeoCaseSetSiteReposCustom extends GeoBaseReposCustom {
	

    
    @PersistenceContext
    public EntityManager entityManager;

    /**
     **線上場地租借-查詢時段的場地名稱
     */    
	public GeoKgoCaseSetSiteMain findSiteNameByTimeId(String rentTimeId ){

    	String sql = "SELECT sm.* "+
                     "FROM GEO_KGO_CASESET_SITE_MAIN sm , GEO_KGO_CASESET_RENT_MAIN rm , "+
                     "     GEO_KGO_CASESET_RENT_DATE rd , GEO_KGO_CASESET_RENT_TIME rt "+
    			     "WHERE rt.rent_date_id = rd.rent_date_id AND rd.case_rent_id = rm.case_rent_id "+
                     "AND rm.service_id = sm.site_main_id  AND rt.rent_time_id = :rentTimeId ";
        Query sqlQuery = entityManager.createNativeQuery(sql,GeoKgoCaseSetSiteMain.class);
        sqlQuery.setParameter("rentTimeId", rentTimeId);
        List<GeoKgoCaseSetSiteMain> list = sqlQuery.getResultList();
        return list.get(0);
    }

    /**
     ** 線上場地查詢
     */
	public List<GeoCaseSetSiteMainModel> findSite(String organId, String unitId, String editUser, String siteName) {
    	StringBuilder sb = new StringBuilder();
		sb.append("select og.OrganName, ut.UnitName, ur.Name as editUserName, sm.site_name, sm.site_amount, sm.site_status, sm.site_main_id ");
		sb.append("from GEO_KGO_CASESET_SITE_MAIN sm ");
		sb.append("inner join KGO_ORGAN og on sm.organ_id = og.OrganId ");
		sb.append("inner join KGO_UNIT ut on sm.unit_id = ut.UnitId and sm.organ_id = ut.OrganId ");
		sb.append("inner join KGO_USER ur on sm.edit_user = ur.UserId ");
		sb.append("inner join KGO_GROUP_LEVEL gl on gl.OrganId = sm.organ_id ");
		sb.append("where (sm.is_delete is NULL or sm.is_delete!= 1) ");
		if(StringUtils.isNotBlank(organId))  sb.append(" and gl.Seq = :organId");
		if(StringUtils.isNotBlank(unitId))   sb.append(" and sm.unit_id = :unitId");
		if(StringUtils.isNotBlank(editUser)) sb.append(" and sm.edit_user = :editUser");
		if(StringUtils.isNotBlank(siteName)) sb.append(" and sm.site_name like CONCAT('%', :siteName,'%') ");

        Query sqlQuery = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(organId)) sqlQuery.setParameter("organId", organId);
        if (StringUtils.isNotBlank(unitId)) sqlQuery.setParameter("unitId", unitId);
        if (StringUtils.isNotBlank(editUser)) sqlQuery.setParameter("editUser", editUser);
        if (StringUtils.isNotBlank(siteName)) sqlQuery.setParameter("siteName", siteName);

		@SuppressWarnings("rawtypes")
		List listData = sqlQuery.getResultList();
        List<GeoCaseSetSiteMainModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
        	GeoCaseSetSiteMainModel model = new GeoCaseSetSiteMainModel();
            Object[] recordArray = (Object[]) listData.get(i);
            model.setOrganName(((String)recordArray[0]));
            model.setUnitName(((String)recordArray[1]));
            model.setEditUser(((String)recordArray[2]));
            model.setSiteName(((String)recordArray[3]));
            model.setSiteAmount(((Integer)recordArray[4]));
            model.setSiteStatus(((Integer)recordArray[5]));
            model.setSiteMainId(((String)recordArray[6]));
            datas.add(model);
        }
		return datas;
	}
    /**
     *  查詢科室所屬之場地建立者
     * */
    public List<GeoSiteEditUserModel> findSiteEditor(String organId , String unit) {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT DISTINCT(a.edit_user), b.Name ");
        sb.append("FROM GEO_KGO_CASESET_SITE_MAIN a ");
        sb.append("INNER JOIN KGO_USER b ON a.edit_user = b.UserId ");
        sb.append("WHERE a.organ_id = :organId AND a.unit_id = :unit ");

        Query sqlQuery = entityManager.createNativeQuery(sb.toString());
        sqlQuery.setParameter("organId", organId);
        sqlQuery.setParameter("unit", unit);
        List listData = sqlQuery.getResultList();
        List<GeoSiteEditUserModel> datas = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < listData.size(); i++) {
            GeoSiteEditUserModel model = new GeoSiteEditUserModel();
            Object[] recordArray = (Object[]) listData.get(i);
            model.setUserId((String)recordArray[0]);
            model.setUserName((String)recordArray[1]);
            datas.add(model);
        }

        return datas;
    }

    /**
     *  查詢當月案件日期
     *  param: caseSetId , siteMainId
     * */
    public List<GeoCaseSetSearchDateModel> searchDateByCaseSet(String caseSetId , String siteMainId , String firstDate , String lastDate) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT rd.rent_date_id,rd.case_rent_id ,rd.detail_date , rd.is_all_day ");
        sb.append("FROM GEO_KGO_CASESET_RENT_DATE rd,GEO_KGO_CASESET_RENT_MAIN rm ");
        sb.append("WHERE rm.case_set_id = :caseSetId AND rm.service_id = :siteMainId AND rm.case_rent_id = rd.case_rent_id ");
        sb.append("AND rd.detail_date >= CONVERT(datetime , :firstDate ) AND rd.detail_date <= CONVERT(DATETIME, :lastDate ) ");
        sb.append("ORDER BY rd.detail_date ASC ");

        Query sqlQuery = entityManager.createNativeQuery(sb.toString());
        sqlQuery.setParameter("caseSetId", caseSetId);
        sqlQuery.setParameter("siteMainId", siteMainId);
        sqlQuery.setParameter("firstDate", firstDate);
        sqlQuery.setParameter("lastDate", lastDate);
        List listData = sqlQuery.getResultList();
        List<GeoCaseSetSearchDateModel> datas = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < listData.size(); i++) {
            GeoCaseSetSearchDateModel model = new GeoCaseSetSearchDateModel();
            Object[] recordArray = (Object[]) listData.get(i);
            model.setRentDateId((String)recordArray[0]);
            model.setCaseRentId((String)recordArray[1]);
            model.setDetailDate(DateUtil.dateToString((Timestamp)recordArray[2],DateUtil.PATTEN_YEAR_MONTH_DAY));
            model.setIsAllDay((Integer)recordArray[3]);
            datas.add(model);
        }

        return datas;
    }


    /**
     *  預約時間id查詢服務案件編號
     * */
    public String findCasesetIdByTimeId(String rentTimeId ) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT rm.case_set_id  FROM GEO_KGO_CASESET_RENT_MAIN rm WHERE rm.case_rent_id = ( ");
        sb.append("     SELECT rd.case_rent_id  FROM GEO_KGO_CASESET_RENT_DATE rd WHERE rd.rent_date_id  = (  ");
        sb.append("		    SELECT rt.rent_date_id FROM GEO_KGO_CASESET_RENT_TIME rt WHERE rt.rent_time_id  = :rentTimeId  ");
        sb.append("     )");
        sb.append(")");

        Query sqlQuery = entityManager.createNativeQuery(sb.toString());

        sqlQuery.setParameter("rentTimeId", rentTimeId);
        return  (String) sqlQuery.getSingleResult();
    }


}
