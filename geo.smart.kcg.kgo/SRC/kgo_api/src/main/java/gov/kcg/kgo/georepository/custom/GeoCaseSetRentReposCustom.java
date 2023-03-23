package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.dto.GeoCaseByOrganModel;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoCasesetRentTime;
import gov.kcg.kgo.geoenum.CaseSetCategoryEnum;
import gov.kcg.kgo.geomodel.GeoCaseSetSiteMainModel;
import gov.kcg.kgo.geomodel.GeoKgoAppointmentOrderQueryModel;
import gov.kcg.kgo.geomodel.GeokgoRentTimeInsertModel;
import gov.kcg.kgo.service.bean.excel.GeoCaseRentalCaseExcelVo;
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
import java.util.*;

@Repository
public class GeoCaseSetRentReposCustom extends GeoBaseReposCustom {
    private final Logger logger = LoggerFactory.getLogger(GeoCaseSetRentReposCustom.class);

    @PersistenceContext
    public EntityManager entityManager;

    public List<GeokgoRentTimeInsertModel> findSiteRentTimeList(String rentDateId , Date queryDate ,String siteMainId ){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  rt.rent_time_id , rt.rent_date_id, rt.start_time,rt.end_time,rt.available_user_qouta,rt.is_locked,rt.used_users_num,rt.unit_price ");
        sb.append("FROM GEO_kgo_caseset_rent_time rt ");
        sb.append("INNER JOIN GEO_KGO_CASESET_RENT_DATE  rd ON rd.rent_date_id = rt.rent_date_id AND rd.detail_date = :queryDate ");
        sb.append("INNER JOIN GEO_KGO_CASESET_RENT_MAIN rm ON rm.case_rent_id = rd.case_rent_id AND rm.service_id = :siteMainId ");
        sb.append("WHERE rt.is_abandon  IS NULL ");
        sb.append("ORDER BY rt.start_time ");

        Query sqlQuery = entityManager.createNativeQuery(sb.toString());
        sqlQuery.setParameter("queryDate", queryDate);
        sqlQuery.setParameter("siteMainId", siteMainId);
        List listData = sqlQuery.getResultList();
        List<GeokgoRentTimeInsertModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeokgoRentTimeInsertModel model = new GeokgoRentTimeInsertModel();
            Object[] recordArray = (Object[]) listData.get(i);
            model.setRentTimeId((String) recordArray[0]);
            model.setRentDateId((String) recordArray[1]);
            model.setStartTime(DateUtil.dateToString((Date)recordArray[2],DateUtil.PATTEN_HOUR_MINUTE));
            model.setEndTime(DateUtil.dateToString((Date)recordArray[3],DateUtil.PATTEN_HOUR_MINUTE));
            model.setAvailableUserQuota((Integer)recordArray[4]);
            model.setIsLocked((Integer)recordArray[5]);
            model.setUsedUsersNum((Integer)recordArray[6]);
            model.setUnitPrice((Integer)recordArray[7]);
            if(rentDateId == null || rentDateId.equals(model.getRentDateId()) == false){
                //鎖定非所屬案件
                Date startTime = (Date) recordArray[2];
                Date endTime = (Date)recordArray[3];
                if(startTime.getHours() == 8 && endTime.getHours() == 21 ){
                    model.setIsLocked(2);//時間間隔超過六小時表示全天佔用
                }else{
                    model.setIsLocked(1);
                }
            }
            datas.add(model);
        }
        return datas;

    }
    /** 前台線上預約 查詢身分所屬案件*/
    public List<GeoKgoAppointmentOrderQueryModel> queryAppointRentalList(String appointmentName, String appointmentIdentity, String appointmentEmail, String appointmentPhone, List<String> casesetCategory){

        //主要欄位資訊
        StringBuilder caseSb = new StringBuilder();
        caseSb.append("WITH TEMPCASE AS ( ");
        caseSb.append(  "SELECT p1.CaseId caseId, p1.ColumnValue Id, p2.ColumnValue phone, p3.ColumnValue email, p4.ColumnValue name ");
        caseSb.append(  "FROM KGO_CASE_DETAIL p1 ,KGO_CASE_DETAIL p2 ,KGO_CASE_DETAIL p3 ,KGO_CASE_DETAIL p4 ");
        caseSb.append(  "WHERE p1.ColumnId = 'ID' AND p1. ColumnValue = :appointId ");
        caseSb.append(  "AND p2.ColumnId ='CellPhone' AND p2.ColumnValue = :appointPhone ");
        caseSb.append(  "AND p3.ColumnId = 'Email' AND p3.ColumnValue = :appointEmail ");
        caseSb.append(  "AND p4.ColumnId = 'Name' AND p4.ColumnValue = :appointName ");
        caseSb.append(  "AND p1.CaseId = p2.CaseId AND p1.CaseId = p3.CaseId  AND p1.CaseId = p4.CaseId ) ");

        caseSb.append("SELECT ts.caseId, ts.Id, ts.phone, ts.email, ts.name ,cm.ApplyDate, cm.Status ,kc.CaseSetName , kc.refund_deadline , kc.caseSet_category , cm.CaseSetId , kc.needPay ");
        caseSb.append("FROM TEMPCASE ts ");
        caseSb.append("INNER JOIN KGO_CASE_MAIN cm on ts.caseId = cm.CaseId ");
        caseSb.append("INNER JOIN KGO_CASESET kc on kc.CaseSetId = cm.CaseSetId AND kc.caseSet_category in ( :categorys ) ");


        Query caseQuery = entityManager.createNativeQuery(caseSb.toString());
        caseQuery.setParameter("appointId",appointmentIdentity);
        caseQuery.setParameter("appointPhone",appointmentPhone);
        caseQuery.setParameter("appointEmail",appointmentEmail);
        caseQuery.setParameter("appointName",appointmentName);
        //查詢類型
        List<String> categorys = new ArrayList<>();
        if(casesetCategory.contains("site"))      categorys.add(CaseSetCategoryEnum.SITE.getValue());
        if(casesetCategory.contains("activity"))  categorys.add(CaseSetCategoryEnum.ACTIVITY.getValue());
        caseQuery.setParameter("categorys",categorys);

        List caseData = caseQuery.getResultList();

        List<GeoKgoAppointmentOrderQueryModel> datas = Collections.synchronizedList(new ArrayList<>());
        for(int i = 0 ; i<caseData.size();i++){
            Object[] recordArray = (Object[]) caseData.get(i);
            GeoKgoAppointmentOrderQueryModel queryModel = new GeoKgoAppointmentOrderQueryModel();
            queryModel.setCaseId((String)recordArray[0]);
            queryModel.setAppointmentId((String)recordArray[1]);
            queryModel.setAppointmentPhone((String)recordArray[2]);
            queryModel.setAppointmentEmail((String)recordArray[3]);
            queryModel.setAppointmentName((String)recordArray[4]);

            Timestamp applyDate = (Timestamp)recordArray[5];
            queryModel.setApplyDate(DateUtil.dateToString(applyDate,DateUtil.PATTEN_YEAR_MONTH_DAY));
            queryModel.setCaseStatus((String)recordArray[6]);
            queryModel.setRefundDeadline((Integer)recordArray[8]);
            queryModel.setCaseSetCategory(CaseSetCategoryEnum.getApplyTypeEnum((String)recordArray[9]).getLabel());
            String caseSetId = (String)recordArray[10];
            logger.info("query rental Case - caseId : "+ queryModel.getCaseId()+" caseSetId :"+caseSetId);
            queryModel.setNeedPay((Boolean)recordArray[11]);

            //存放租借相關欄位
            queryModel = queryTimeRentalDetail(caseSetId,queryModel);
            datas.add(queryModel);
        }
        return datas;
    }
    /** QRcode查詢caseId所屬案件*/
    public GeoKgoAppointmentOrderQueryModel queryAppointByCaseId(String caseId){

        //主要欄位資訊
        StringBuilder caseSb = new StringBuilder();
        caseSb.append("WITH TEMPCASE AS ( ");
        caseSb.append(" SELECT p1.CaseId caseId ,p1.ColumnValue Id,p2.ColumnValue phone ,p3.ColumnValue email , p4.ColumnValue name ");
        caseSb.append(" FROM KGO_CASE_DETAIL p1 ,KGO_CASE_DETAIL p2 ,KGO_CASE_DETAIL p3 ,KGO_CASE_DETAIL p4 ");
        caseSb.append(" WHERE p1.ColumnId = 'ID'  AND p2.ColumnId ='CellPhone'  AND p1.CaseId = p2.CaseId ");
        caseSb.append(" AND p3.ColumnId = 'Email' AND p4.ColumnId = 'Name'   AND p1.CaseId = p3.CaseId ");
        caseSb.append(" AND p1.CaseId = p4.CaseId AND p1.CaseId = :caseId  ) ");

        caseSb.append("SELECT ts.caseId, ts.Id, ts.phone, ts.email, ts.name ,cm.ApplyDate, cm.Status ,kc.CaseSetName , kc.refund_deadline , kc.caseSet_category  , cm.CaseSetId , kc.needPay ");
        caseSb.append("FROM TEMPCASE ts ");
        caseSb.append("INNER JOIN KGO_CASE_MAIN cm on ts.caseId = cm.CaseId  ");
        caseSb.append("INNER JOIN KGO_CASESET kc on kc.CaseSetId = cm.CaseSetId  ");

        Query caseQuery = entityManager.createNativeQuery(caseSb.toString());
        caseQuery.setParameter("caseId",caseId);
        //查詢類型
        List<String> categorys = new ArrayList<>();
        List caseData = caseQuery.getResultList();

        Object[] recordArray = (Object[]) caseData.get(0);
        GeoKgoAppointmentOrderQueryModel queryModel = new GeoKgoAppointmentOrderQueryModel();
        queryModel.setCaseId((String)recordArray[0]);
        queryModel.setAppointmentId((String)recordArray[1]);
        queryModel.setAppointmentPhone((String)recordArray[2]);
        queryModel.setAppointmentEmail((String)recordArray[3]);
        queryModel.setAppointmentName((String)recordArray[4]);

        Timestamp applyDate = (Timestamp)recordArray[5];
        queryModel.setApplyDate(DateUtil.dateToString(applyDate,DateUtil.PATTEN_YEAR_MONTH_DAY));
        queryModel.setCaseStatus((String)recordArray[6]);
        queryModel.setRefundDeadline((Integer)recordArray[8]);
        queryModel.setCaseSetCategory(CaseSetCategoryEnum.getApplyTypeEnum((String)recordArray[9]).getLabel());
        String caseSetId = (String)recordArray[10];
        logger.info("query rental Case - caseId : "+ queryModel.getCaseId()+" caseSetId :"+caseSetId);
        queryModel.setNeedPay((Boolean)recordArray[11]);
        //存放租借相關欄位
        queryModel = queryTimeRentalDetail(caseSetId,queryModel);
        return queryModel;
    }

    /**
     * 租借相關欄位查詢
     * */
    private GeoKgoAppointmentOrderQueryModel queryTimeRentalDetail( String caseSetId ,GeoKgoAppointmentOrderQueryModel model ){
        StringBuilder timeSb = new StringBuilder();

        timeSb.append("select rr.rent_status, rr.start_time, rr.end_time, rp.pay_amount, sm.site_name, rp.payment_status, rp.pay_type  ");
        timeSb.append("from GEO_KGO_CASE_RENT_RELATION rr ");
        timeSb.append("left join GEO_KGO_CASESET_RENT_MAIN rm on rm.case_set_id = :caseSetId ");
        timeSb.append("left join GEO_KGO_CASESET_SITE_MAIN sm on rm.service_id = sm.site_main_id ");
        timeSb.append("left join GEO_KGO_CASE_RENT_PAYMENT rp  on rp.case_id = rr.case_id ");
        timeSb.append("where rr.case_id = :caseId ");

        Query query = entityManager.createNativeQuery(timeSb.toString());
        query.setParameter("caseSetId",caseSetId);
        query.setParameter("caseId",model.getCaseId());

        Object[] data =(Object[]) query.getResultList().get(0);
        if(ObjectUtils.anyNotNull(data)){
            for(int i = 0; i<data.length ; i++){
                model.setRentalStatus((String)data[0]);
                model.setDateStart(DateUtil.dateToString((Timestamp)data[1],DateUtil.PATTEN_FULL_TIME));
                model.setDateEnd(DateUtil.dateToString((Timestamp)data[2],DateUtil.PATTEN_FULL_TIME));
                model.setPayAmount((Integer)data[3]);
                model.setRentalObject((String)data[4]);
                model.setPaymentStatus((String)data[5]);
                model.setPayType((String)data[6]);
            }
        }else{
            logger.error("Rent Case query rentTime data error ...");
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.DATA_FORMAT_ERROR));
        }
        return model;
    }


    public GeoCaseSetSiteMainModel findSiteMainByTimeId(String rentTimeId){
        StringBuilder sql = new StringBuilder();

        sql.append("select sm.site_main_id , sm.organ_id , sm.unit_id , sm.site_name , sm.site_amount ,sm.site_status , sm.serviceHtml  from GEO_KGO_CASESET_SITE_MAIN sm, GEO_KGO_CASESET_RENT_MAIN rm, ");
        sql.append("GEO_KGO_CASESET_RENT_DATE rd , GEO_KGO_CASESET_RENT_TIME rt ");
        sql.append("WHERE rt.rent_date_id = rd.rent_date_id ");
        sql.append("AND  rd.case_rent_id = rm.case_rent_id ");
        sql.append("AND rm.service_id = sm.site_main_id ");
        sql.append("AND rt.rent_time_id = :rentTimeId ");

        Query caseQuery = entityManager.createNativeQuery(sql.toString());
        caseQuery.setParameter("rentTimeId",rentTimeId);
        List listData = caseQuery.getResultList();

        GeoCaseSetSiteMainModel model = new GeoCaseSetSiteMainModel();
        Object[] recordArray = (Object[]) listData.get(0);
        model.setSiteMainId(((String)recordArray[0]));
        model.setOrganId(((String)recordArray[1]));
        model.setUnitId(((String)recordArray[2]));
        model.setSiteName(((String)recordArray[3]));
        model.setSiteAmount(((Integer)recordArray[4]));
        model.setSiteStatus(((Integer)recordArray[5]));
        model.setServiceHtml(((String)recordArray[6]));

        return model;

    }


    public List<GeoCaseRentalCaseExcelVo> findAllRentableCase(boolean isLock , String casesetName, String casesetId, String organId, String unitId, String siteMainId, String periodFrom, String periodTo, String siteType ){
        StringBuilder sql = new StringBuilder();
        sql.append("WITH CASESET_BY_NAME AS ( ");
        sql.append("  select CaseSetId, CaseSetName, OwnerOrgan from KGO_CASESET  where caseset_category in ( 'site', 'activity' ) AND CaseSetName like CONCAT('%', :casesetName, '%')");
        sql.append(" )");

        sql.append("select rm.case_set_id, cn.CaseSetName, cn.OwnerOrgan, sm.unit_id, sm.site_name, rd.detail_date, rd.rent_date_id ");
        sql.append("from  GEO_KGO_CASESET_RENT_DATE rd ");
        sql.append("left join GEO_KGO_CASESET_RENT_MAIN rm on rm.case_rent_id = rd.case_rent_id ");
        sql.append("left join GEO_KGO_CASESET_SITE_MAIN  sm on  sm.site_main_id = rm.service_id ");
        sql.append(", CASESET_BY_NAME cn ");


        sql.append("where EXISTS ( ");
        sql.append(        "select  rt.rent_time_id  from GEO_KGO_CASESET_RENT_TIME rt " );
        sql.append(        "where rt.rent_date_id = rd.rent_date_id " );
        sql.append(        "AND rt.is_abandon is null ");
        if(isLock){
            sql.append("AND ( rt.is_locked = 1  OR rt.used_users_num > 0 )  ) ");
        }else{
            sql.append("AND rt.is_locked is null )  ");
        }
        sql.append("AND rm.case_set_id in ( cn.CaseSetId ) ");

        if(StringUtils.isNotBlank(casesetId)){
            sql.append("AND rm.case_set_id = :casesetId ");
        }
        if(StringUtils.isNotBlank(organId)){
            sql.append("AND cn.OwnerOrgan = :organId ");
        }
        if(StringUtils.isNotBlank(unitId)){
            sql.append("AND sm.unit_id = :unitId ");
        }
        if(StringUtils.isNotBlank(siteMainId)){
             sql.append("AND sm.site_main_id = :siteMainId ");
        }
        if (StringUtils.isNotBlank(periodFrom) &&StringUtils.isNotBlank(periodTo)) {
             sql.append("AND convert(varchar, rd.detail_date, 112) >= :periodFrom  ");
             sql.append("AND convert(varchar, rd.detail_date, 112) <= :periodTo  ");
        }
        if(StringUtils.isNotBlank(siteType)){
            sql.append("AND sm.site_type = :siteType ");
        }
        sql.append("order by rm.case_set_id, sm.site_name, rd.detail_date  ");



        Query query = entityManager.createNativeQuery(sql.toString());

        query.setParameter("casesetName",StringUtils.defaultIfBlank(casesetName,""));

        if(StringUtils.isNotBlank(casesetId)){
            query.setParameter("casesetId",casesetId);
        }

        if(StringUtils.isNotBlank(organId)){
            query.setParameter("organId",organId);
        }
        if(StringUtils.isNotBlank(unitId)){
            query.setParameter("unitId",unitId);
        }
        if(StringUtils.isNotBlank(siteMainId)){
            query.setParameter("siteMainId",siteMainId);
        }
        if (StringUtils.isNotBlank(periodFrom) &&StringUtils.isNotBlank(periodTo)) {
            query.setParameter("periodFrom",periodFrom);
            query.setParameter("periodTo",periodTo);
        }
        if(StringUtils.isNotBlank(siteType)){
            query.setParameter("siteType",siteType);
        }

        //Column : rm.case_set_id, cn.CaseSetName, sm.organ_id, sm.unit_id, sm.site_name, rd.detail_date, rd.rent_date_id
        List result = query.getResultList();
        List<GeoCaseRentalCaseExcelVo> datas = Collections.synchronizedList(new ArrayList<>());
        for(int i = 0; i < result.size() ; i++){
            GeoCaseRentalCaseExcelVo data = new GeoCaseRentalCaseExcelVo();
            Object[] recordArray = (Object[]) result.get(i);
            data.setOrder(i+1);
            data.setCaseSetId((String)recordArray[0]);
            data.setCaseSetName((String)recordArray[1]);
            data.setOrganId((String)recordArray[2]);
            data.setUnitId((String)recordArray[3]);
            data.setSiteName((String)recordArray[4]);
            data.setRentDate(DateUtil.timestampToString((Timestamp)recordArray[5],DateUtil.PATTEN_YEAR_MONTH_DAY));
            data.setRentDateId((String)recordArray[6]);
            datas.add(data);
        }
         return datas;
    }

    public List<GeoKgoCasesetRentTime> findRentableTimeByRentDate(boolean isLock, String rentDateId){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM GEO_KGO_CASESET_RENT_TIME ");
        sql.append("WHERE is_abandon IS null ");
        sql.append("AND rent_date_id = :rentDateId ");
        if(isLock){
            sql.append("AND ( is_locked = 1  OR used_users_num > 0 ) ");
        }else{
            sql.append("AND is_locked IS null ");
        }
        sql.append("ORDER BY start_time asc ");

        Query query = entityManager.createNativeQuery(sql.toString(), GeoKgoCasesetRentTime.class);
        query.setParameter("rentDateId", rentDateId);

        return query.getResultList();
    }



    public List<GeoCaseByOrganModel> getCaseDetailColumnByCaseSetId(String caseId) {
        List<GeoCaseByOrganModel> geoCaseByOrganModels = new ArrayList<>();
        StringBuilder sql  = new StringBuilder();
        sql.append("SElECT distinct kcdm.ColumnName, kdl.CaseId, kdl.ColumnId, kdl.ColumnValue,  ");
        sql.append("kdl.Version,kcm.CaseSetId ");
        sql.append("from KGO_CASE_DETAIL kdl ");
        sql.append("left join KGO_CASE_MAIN kcm on kcm.CaseId = kdl.CaseId  ");
        sql.append("left join KGO_CASESET_COLUMN kcdm on kcdm.CaseSetId = kcm.CaseSetId ");
        sql.append("and kcdm.Version = kdl.Version ");
        sql.append("and kcdm.ColumnId = kdl.ColumnId ");
        sql.append("where  kdl.Version = (select Max(Version)  from KGO_CASE_DETAIL cv where cv.CaseId = kdl.CaseId  ) ");
        sql.append("AND kdl.CaseId = :caseId ");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("caseId", caseId);
        List listData = query.getResultList();
        if (listData!=null && listData.size()>0){
            for (int i=0;i<listData.size();i++){
                Object[] recordArray = (Object[]) listData.get(i);
                GeoCaseByOrganModel model = new GeoCaseByOrganModel();
                model.setColumnName((String)recordArray[0]);
                model.setCaseId((String)recordArray[1]);
                model.setColumnId((String)recordArray[2]);
                model.setColumnValue((String)recordArray[3]);
                model.setVersion((Integer) recordArray[4]);
                model.setCaseSetId((String) recordArray[5]);
                geoCaseByOrganModels.add(model);
            }
        }
        return geoCaseByOrganModels;
    }




}
