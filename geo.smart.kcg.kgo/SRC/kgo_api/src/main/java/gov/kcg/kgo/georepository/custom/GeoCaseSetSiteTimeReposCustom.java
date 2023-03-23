package gov.kcg.kgo.georepository.custom;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import gov.kcg.kgo.dto.CaseMainQueryCaseViewListDto;
import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.geomodel.dto.SiteCaseMainQueryViewListDto;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.enums.backend.CaseMainStatusEnum;
import gov.kcg.kgo.geomodel.GeoCaseSetSiteTimeModel;
import gov.kcg.kgo.geoviewmodel.backend.rq.ExportCaseSiteExcelRq;
import org.springframework.util.CollectionUtils;


@Repository
public class GeoCaseSetSiteTimeReposCustom extends GeoBaseReposCustom {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseSetSiteTimeReposCustom.class);
    
    @PersistenceContext
    public EntityManager entityManager;



	public List<CaseMainQueryCaseViewListDto> caseViewSAQuery(String role, String loginUserId, String organId, String userName, String dateStart, String dateEnd, String caseSetName, String caseId,
															  List<String> status, String caseFlowType, String id, String cellPhone, String timeStart, String timeEnd , String siteName,Integer version) {
		StringBuilder sb = new StringBuilder();
		sb.append("select m.CaseId CASE_ID, m.ApplyDate APPLY_DATE, ISNULL(m.ApplyUserName, m.account) APPLY_USER, s.CaseSetName CASESET_NAME, ");
		sb.append("m.DeadlineDate DEADLINE_DATE, m.Status STATUS, '' TYPE, u2.Name CASE_OFFICER, m.processId PROCESS_ID, s.AcceptSet ACCEPT_SET, ");
		sb.append("s.CaseSetId CASE_SET_ID, m.fDate FDATE, s.CaseFlowType CASE_FLOW_TYPE, m.ApplyUserLoginType APPLY_USER_LOGIN_TYPE , ");
		sb.append("sm.site_name SITE_NAME, rl.start_time START_TIME,rp.pay_amount AMOUNT , rl.rent_status , rp.payment_status, d.Version  ");
		sb.append("from KGO_CASE_MAIN m  ");
		sb.append("left join KGO_CASE_DETAIL d on m.CaseId=d.CaseId and d.ColumnId = 'ID' ");
		sb.append("inner join KGO_CASESET s on m.CaseSetId=s.CaseSetId  and s.caseSet_category in ( 'site','activity' ) ");
		sb.append("left join GEO_KGO_CASE_RENT_RELATION rl on rl.case_id = m.CaseId  ");
		sb.append("left join GEO_KGO_CASESET_SITE_MAIN sm on sm.site_main_id = rl.site_main_id  ");
		sb.append("left join GEO_KGO_CASE_RENT_PAYMENT rp on rp.case_id = m.CaseId  ");
		sb.append("left join KGO_USER u2 on m.caseOfficer = u2.UserId ");

		if (KgoRoleEnum.CASE_M.getValue().equalsIgnoreCase(role)) {
			sb.append("inner join KGO_CASESET_MANAGER cm on cm.CaseSetId = s.CaseSetId  ");
		}
		sb.append("where 1=1 ");

		if (ObjectUtils.isNotEmpty(version)) {
			sb.append("and d.Version = :version ");
		}
		if (StringUtils.isNotBlank(userName)) {
			sb.append("and m.ApplyUserName like concat('%', :userName, '%') ");
		}
		if (StringUtils.isNotBlank(dateStart)) {
			sb.append("and convert(varchar, m.ApplyDate, 112) >= :dateStart  ");
		}
		if (StringUtils.isNotBlank(dateEnd)) {
			sb.append("and convert(varchar, m.ApplyDate, 112) <= :dateEnd  ");
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			sb.append("and s.CaseSetName like concat('%', :caseSetName, '%') ");
		}
		if (StringUtils.isNotBlank(caseId)) {
			sb.append("and :caseId in (m.CaseId,m.OCaseId) ");
		}
		if (StringUtils.isNotBlank(caseFlowType)) {
			sb.append("and s.CaseFlowType = :caseFlowType ");
		}
		if (!CollectionUtils.isEmpty(status)) {
			sb.append("and m.status in :status ");
		}
		if (StringUtils.isNotBlank(id)) {
			sb.append("and m.ApplyUser = :id ");
		}
		if (StringUtils.isNotBlank(cellPhone)) {
			sb.append("and m.CellPhone = :cellPhone ");
		}
		if (KgoRoleEnum.UNIT_U.getValue().equalsIgnoreCase(role)) {
			sb.append("and m.CaseOfficer = :loginUserId ");
		}
		if (KgoRoleEnum.CASE_M.getValue().equalsIgnoreCase(role)) {
			sb.append("and cm.Manager = :loginUserId ");
		}
		if (KgoRoleEnum.UNIT_A.getValue().equalsIgnoreCase(role) || KgoRoleEnum.UNIT_M.getValue().equalsIgnoreCase(role)) {
			sb.append("and m.CaseOrgan = :organId ");
		}
		if(StringUtils.isNotBlank(siteName)){
			sb.append("and sm.site_name = :siteName ");
		}

		if (StringUtils.isNotBlank(timeStart)) {
			sb.append("and convert(varchar, rl.start_time, 112) >= :timeStart  ");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			sb.append("and convert(varchar, rl.start_time, 112) <= :timeEnd  ");
		}

		sb.append("GROUP BY m.CaseId, m.ApplyDate, m.ApplyUserName, m.account,s.CaseSetName,m.DeadlineDate, m.Status,u2.Name, m.processId,\n" +
				"         s.AcceptSet,s.CaseSetId,m.fDate,s.CaseFlowType,m.ApplyUserLoginType,sm.site_name , rl.start_time ,rp.payment_status , rl.rent_status ,rp.pay_amount, d.Version " +
				"Order By m.CaseId desc");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString());
		if (ObjectUtils.isNotEmpty(version)) {
			query.setParameter("version", version);
		}
		if (StringUtils.isNotBlank(userName)) {
			query.setParameter("userName", userName);
		}
		if (StringUtils.isNotBlank(dateStart)) {
			query.setParameter("dateStart", dateStart);
		}
		if (StringUtils.isNotBlank(dateEnd)) {
			query.setParameter("dateEnd", dateEnd);
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			query.setParameter("caseSetName", caseSetName);
		}
		if (StringUtils.isNotBlank(caseId)) {
			query.setParameter("caseId", caseId);
		}
		if (!CollectionUtils.isEmpty(status)) {
			query.setParameter("status", status);
		}
		if (KgoRoleEnum.UNIT_U.getValue().equalsIgnoreCase(role) || KgoRoleEnum.CASE_M.getValue().equalsIgnoreCase(role)) {
			query.setParameter("loginUserId", loginUserId);
		}
		if (KgoRoleEnum.UNIT_A.getValue().equalsIgnoreCase(role) || KgoRoleEnum.UNIT_M.getValue().equalsIgnoreCase(role)) {
			query.setParameter("organId", organId);
		}
		if (StringUtils.isNotBlank(caseFlowType)) {
			query.setParameter("caseFlowType", caseFlowType);
		}
		if (StringUtils.isNotBlank(id)) {
			query.setParameter("id", id);
		}
		if (StringUtils.isNotBlank(cellPhone)) {
			query.setParameter("cellPhone", cellPhone);
		}
		if(StringUtils.isNotBlank(siteName)){
			query.setParameter("siteName", siteName);
		}
		if (StringUtils.isNotBlank(timeStart)) {
			query.setParameter("timeStart",timeStart);
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			query.setParameter("timeEnd",timeEnd);
		}
		List dataList = query.getResultList();
		List<CaseMainQueryCaseViewListDto> dtoList = Collections.synchronizedList(new ArrayList<>());
		for(int i = 0;i<dataList.size(); i++){
			SiteCaseMainQueryViewListDto model = new SiteCaseMainQueryViewListDto();
			Object[] recordArray = (Object[]) dataList.get(i);
			model.setCaseId((String)recordArray[0]);
			model.setApplyDate((Timestamp) recordArray[1]);
			model.setApplyUser((String)recordArray[2]);
			model.setCaseSetName((String)recordArray[3]);
			Date deadLineDate = (Date) recordArray[4];
			model.setDeadlineDate(new Timestamp(deadLineDate.getTime()));
			model.setStatus((String)recordArray[5]);
			model.setType((String)recordArray[6]);
			model.setCaseOfficer((String)recordArray[7]);
			model.setProcessId((String)recordArray[8]);
			model.setAcceptSet((String)recordArray[9]);
			model.setCaseSetId((String)recordArray[10]);
			model.setFdate((Timestamp)recordArray[11] );
			model.setCaseFlowType((String)recordArray[12]);
			model.setApplyUserLoginType((String)recordArray[13]);
			model.setSiteName((String)recordArray[14]);
			model.setStartTime((Timestamp) recordArray[15]);
			model.setAmount((Integer)recordArray[16]);
			model.setRentStatus((String)recordArray[17]);
			model.setPaymentStatus((String)recordArray[18]);
			model.setVersion((Integer)recordArray[19]);
			dtoList.add(model);
		}
		return dtoList;
	}

	public Boolean updateUserReserve(String rentTimeId, Integer reserve) {
		if(rentTimeId == null || reserve == null){
			return false;
		}
		String sql = "SELECT   ( available_user_qouta - used_users_num ) REMAIND  " +
				     "FROM GEO_KGO_CASESET_RENT_TIME  WITH (UPDLOCK) " +
					 "WHERE rent_time_id  =  :rentTimeId";

		Query sqlQuery = entityManager.createNativeQuery(sql);
		sqlQuery.setParameter("rentTimeId", rentTimeId);
		int quotaRemain = Integer.parseInt(sqlQuery.getSingleResult().toString());

		//預定名額且尚有餘額 || 退還名額
		if(quotaRemain > 0 && ( quotaRemain - reserve ) > 0 ) {
			String sql2 = "UPDATE GEO_KGO_CASESET_RENT_TIME SET used_users_num = (used_users_num + :reserve )  " +
						  "WHERE rent_time_id  =  :rentTimeId ";
			Query sqlQuery2 = entityManager.createNativeQuery(sql2);
			sqlQuery2.setParameter("rentTimeId", rentTimeId);
			sqlQuery2.setParameter("reserve",reserve);
			return sqlQuery2.executeUpdate() == 1 ? true : false ;
		}else if (quotaRemain > 0 && ( quotaRemain - reserve ) == 0){
			String sql2 = "UPDATE GEO_KGO_CASESET_RENT_TIME SET used_users_num = (used_users_num + :reserve ), is_locked = 1 " +
					"WHERE rent_time_id  =  :rentTimeId ";
			Query sqlQuery2 = entityManager.createNativeQuery(sql2);
			sqlQuery2.setParameter("rentTimeId", rentTimeId);
			sqlQuery2.setParameter("reserve",reserve);
			return sqlQuery2.executeUpdate() == 1 ? true : false ;
		}else {
			return false;
		}
	}
	//取消預約 場地解鎖
	public boolean cancelSiteCase(String rentTimeId){
		if(rentTimeId == null ){
			LOGGER.info("cancelSiteCase rentTimeid cant be null ...");
			return false;
		}
		String sql = "UPDATE GEO_KGO_CASESET_RENT_TIME SET is_locked = null  " +
					 "WHERE rent_time_id = :rentTimeId ";
		Query sqlQuery = entityManager.createNativeQuery(sql);
		sqlQuery.setParameter("rentTimeId", rentTimeId);
		if(sqlQuery.executeUpdate() !=1 ){
			LOGGER.info("cancelSiteCase data error cant update TABLE: GEO_KGO_CASESET_RENT_TIME rentTimeID :"+rentTimeId+
					" is_locked ");
			return false;
		}
		return true;


	}
	//取消預約 減少使用人數
	public boolean cancelCaseRelease(String rentTimeId){
		if(rentTimeId == null ){
			LOGGER.info("cacelcaseRelease rentTimeid cant be null ...");
			return false;
		}
		String sql = "UPDATE GEO_KGO_CASESET_RENT_TIME SET used_users_num = (used_users_num - 1)  " +
					 "WHERE rent_time_id = :rentTimeId AND used_users_num != 0;";
		Query sqlQuery = entityManager.createNativeQuery(sql);
		sqlQuery.setParameter("rentTimeId", rentTimeId);
		if(sqlQuery.executeUpdate() !=1 ){
			LOGGER.info("cacelcaseRelease data error cant update TABLE: GEO_KGO_CASESET_RENT_TIME rentTimeID :"+rentTimeId+
					"userd_users_num ");
			return false;
		}
		return true;


	}


}
