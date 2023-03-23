package gov.kcg.kgo.repository.custom.impl;

import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import gov.kcg.kgo.dto.*;
import gov.kcg.kgo.geomodel.GeoCaseSetApplyCountModel;
import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireCaseSetModel;
import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireMainModel;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import gov.kcg.kgo.enums.backend.CaseMainStatusEnum;
import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.model.KgoOrgan;
import gov.kcg.kgo.repository.KgoOrganRepository;
import gov.kcg.kgo.repository.custom.KgoCaseMainRepositoryCustom;
import gov.kcg.kgo.repository.custom.condition.KgoCaseMainQueryCondition;
import gov.kcg.kgo.repository.custom.condition.config.QueryParamSql;

public class KgoCaseMainRepositoryCustomImpl implements KgoCaseMainRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoCaseMainRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	@Autowired
	KgoOrganRepository kgoOrganRepository;

	private static final String COMMON_SA_SELECT_STR = "select m.CaseId CASE_ID, m.ApplyDate APPLY_DATE, u.Name APPLY_USER, s.CaseSetName CASESET_NAME, m.DeadlineDate DEADLINE_DATE, m.Status STATUS, '' TYPE, '' PROCESS_ID, s.AcceptSet ACCEPT_SET, s.CaseSetId CASE_SET_ID ";
	private static final String COMMON_SA_FROM_STR = "from KGO_CASE_MAIN m left join KGO_CASESET s on m.CaseSetId=s.CaseSetId inner join KGO_USER u on u.UserId = m.ApplyUser  ";
	private static final String COMMON_SA_ORDER_STR = "order by m.UpdateTime desc";

	/**
	 * [已審核匣]
	 *
	 * 查詢該UNIT_U (機關承辦人)身分底下所擁有的[已審核] 案件
	 *
	 * @param reviewer
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @param status
	 * @return
	 */
	@Override
	public List<CaseMainQueryDto> reviewedSAQueryWithUnitU(String reviewer, String userName, String dateStart, String dateEnd, String caseSetName, String caseId, String status) {

		StringBuilder sb = new StringBuilder();

		sb.append(COMMON_SA_SELECT_STR);
		sb.append(COMMON_SA_FROM_STR);
		sb.append(getSACommonWhereStr(userName, dateStart, dateEnd, caseSetName, caseId, status));
		if (StringUtils.isNotBlank(reviewer)) {
			sb.append("and m.Reviewer=:reviewer ");
		}
		sb.append(COMMON_SA_ORDER_STR);

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), CaseMainQueryDto.class);
		setSAQueryCommonParameters(query, userName, dateStart, dateEnd, caseSetName, caseId, status);
		if (StringUtils.isNotBlank(reviewer)) {
			query.setParameter("reviewer", reviewer);
		}

		return query.getResultList();
	}

	/**
	 * [已審核匣]
	 *
	 * 查詢 UNIT_M (機關管理者) 身分底下所擁有的[已審核]案件
	 *
	 * @param reviewer
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @return
	 */
	@Override
	public List<CaseMainQueryDto> reviewedUraScaQueryWithUnitM(String reviewer, String userName, String dateStart, String dateEnd, String caseSetName, String caseId) {

		StringBuilder sb = new StringBuilder();
		// SCA
		sb.append(
				"select CASE_ID, APPLY_DATE, u.Name APPLY_USER, s.CaseSetName CASESET_NAME, DEADLINE_DATE, ta.[STATUS], TYPE, '' CASE_OFFICER, '' PROCESS_ID, s.AcceptSet ACCEPT_SET, s.CaseSetId CASE_SET_ID from ( ");
		sb.append(
				"select sa.CaseId CASE_ID, sa.CreateTime APPLY_DATE, sa.CreateUser CREATE_USER, null DEADLINE_DATE, sa.Status [STATUS], 'SCA' TYPE, sa.UpdateTime UPDATE_TIME, sa.CaseSetId CASESET_ID ");
		sb.append("from KGO_SCA_APPLY sa inner join KGO_APPLY_REVIEWER r on r. CaseId=sa.CaseId ");
		sb.append("where r.IsReview=1 and r.Reviewer =:reviewer ");
		sb.append("union ");
		// URA
		sb.append(
				"select ua.CaseId CASE_ID, ua.CreateTime APPLY_DATE, ua.CreateUser CREATE_USER, null DEADLINE_DATE, ua.Status [STATUS], 'URA' TYPE, ua.UpdateTime UPDATE_TIME, ua.CaseSetId CASESET_ID, s.AcceptSet ACCEPT_SET ");
		sb.append("from KGO_URA_APPLY ua inner join KGO_APPLY_REVIEWER r on r.CaseId=ua. CaseId ");
		sb.append("where r.IsReview=1 and r.Reviewer =:reviewer ");
		sb.append(") ta left join KGO_CASESET s on ta.CASESET_ID=s.CaseSetId ");
		sb.append(" inner join KGO_USER u on u.UserId = ta.CREATE_USER ");
		sb.append("where 1=1  ");
		if (StringUtils.isNotBlank(userName)) {
			sb.append("and u.Name like concat('%', :userName, '%') ");
		}
		if (StringUtils.isNotBlank(dateStart)) {
			sb.append("and convert(varchar, APPLY_DATE, 112) >= :dateStart  ");
		}
		if (StringUtils.isNotBlank(dateEnd)) {
			sb.append("and convert(varchar, APPLY_DATE, 112) <= :dateEnd ");
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			sb.append("and s.CaseSetName like concat('%', :caseSetName, '%') ");
		}
		if (StringUtils.isNotBlank(caseId)) {
			sb.append("and ta.CASE_ID = :caseId ");
		}
		sb.append("Order by ta.UPDATE_TIME desc ");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), CaseMainQueryDto.class);
		query.setParameter("reviewer", reviewer);
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
		return query.getResultList();
	}

	/**
	 * [案件檢視]
	 *
	 * 服務案件新增、系統權限申請(URA、SCA) - 不限角色
	 *
	 * @param isNotAdmin
	 * @param applyUser
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @param status
	 * @return
	 */
	@Override
	public List<CaseMainQueryCaseViewDto> caseViewUraScaQuery(boolean isNotAdmin, String applyUser, String userName, String dateStart, String dateEnd, String caseSetName, String caseId,
			String status) {

		StringBuilder sb = new StringBuilder();

		sb.append(
				"select CASE_ID, APPLY_DATE, u.Name APPLY_USER, ta.CaseSetName CASESET_NAME, DEADLINE_DATE, ta.[STATUS], TYPE, '' CASE_OFFICER, '' PROCESS_ID, '' ACCEPT_SET, '' CASE_SET_ID, null FDATE from ( ");
		sb.append(
				"select sa.CaseId CASE_ID, sa.CreateTime APPLY_DATE, sa.CreateUser CREATE_USER, null DEADLINE_DATE, sa.Status [STATUS], 'SCA' TYPE, sa.UpdateTime UPDATE_TIME, sa.CaseSetId CASESET_ID, sa.CaseSetName ");
		sb.append("from KGO_SCA_APPLY sa ");
		if (isNotAdmin) {
			sb.append("where sa.ApplyUser=:applyUser ");
		}
		sb.append("union ");
		sb.append(
				"select ua.CaseId CASE_ID, ua.CreateTime APPLY_DATE, ua.CreateUser CREATE_USER, null DEADLINE_DATE, ua.Status [STATUS], 'URA' TYPE, ua.UpdateTime UPDATE_TIME, ua.CaseSetId CASESET_ID, '' CaseSetName ");
		sb.append("from KGO_URA_APPLY ua ");
		if (isNotAdmin) {
			sb.append("where ua.ApplyUser=:applyUser ");
		}
		sb.append(") ta " +
//						"left join KGO_CASESET s on ta.CASESET_ID=s.CaseSetId " +
				"left join KGO_USER u on u.UserId = ta.CREATE_USER ");
		sb.append("where 1=1  ");
		if (StringUtils.isNotBlank(userName)) {
			sb.append("and u.Name like concat('%', :userName, '%') ");
		}
		if (StringUtils.isNotBlank(dateStart)) {
			sb.append("and convert(varchar, APPLY_DATE, 112) >= :dateStart  ");
		}
		if (StringUtils.isNotBlank(dateEnd)) {
			sb.append("and convert(varchar, APPLY_DATE, 112) <= :dateEnd ");
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			sb.append("and ta.CaseSetName like concat('%', :caseSetName, '%') ");
		}
		if (StringUtils.isNotBlank(caseId)) {
			sb.append("and ta.CASE_ID = :caseId ");
		}
		if (StringUtils.isNotBlank(status)) {
			sb.append("and ta.STATUS = :status ");
		}
		sb.append("Order by ta.UPDATE_TIME desc ");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), CaseMainQueryCaseViewDto.class);
		if (StringUtils.isNotBlank(applyUser)) {
			query.setParameter("applyUser", applyUser);
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
		if (StringUtils.isNotBlank(status)) {
			query.setParameter("status", status);
		}
		return query.getResultList();
	}

	/**
	 * [案件檢視] 服務申辦流程(SA)
	 *
	 * common columns: userName, dateStart, dateEnd, caseSetName, caseId, status
	 *
	 * ADMIN : role = "", loginUserId = "", organId = "";
	 *
	 * UNIT_U : role = "UNIT_U", loginUserId = caseOfficer = "XX", organId = "";
	 *
	 * CASE_M : role = "CASE_M", loginUserId = Manager = "XX", organId = "";
	 *
	 * UNIT_A & UNIT_M : role = "UNIT_A" or "UNIT_M", loginUserId = "", organId =
	 * "XX";
	 *
	 * @param role
	 * @param loginUserId
	 * @param organId
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @param status
	 * @return
	 */
	@Override
	public List<CaseMainQueryCaseViewListDto> caseViewSAQuery(String role, String loginUserId, String organId, String userName, String dateStart, String dateEnd, String caseSetName, String caseId,
			List<String> status, String caseFlowType, String id, String cellPhone, Integer version) {
		/** GEO 20211102 add 申請人登入方式 & 欄位勾選 */
		StringBuilder sb = new StringBuilder();

		sb.append(
				"select m.CaseId CASE_ID, m.ApplyDate APPLY_DATE, ISNULL(m.ApplyUserName, m.account) APPLY_USER, s.CaseSetName CASESET_NAME, " +
						"m.DeadlineDate DEADLINE_DATE, m.Status STATUS, '' TYPE, u2.Name CASE_OFFICER, m.processId PROCESS_ID, s.AcceptSet ACCEPT_SET, " +
						"s.CaseSetId CASE_SET_ID, m.fDate FDATE, s.CaseFlowType CASE_FLOW_TYPE, m.ApplyUserLoginType APPLY_USER_LOGIN_TYPE, d.Version VERSION ");
		sb.append("from KGO_CASE_MAIN m ");
		sb.append("left join KGO_CASE_DETAIL d on m.CaseId=d.CaseId and d.ColumnId = 'ID' ");
		sb.append("left join KGO_CASESET s on m.CaseSetId=s.CaseSetId ");
//		sb.append("left join ( " +
//				"SELECT ColumnValue, ColumnId, CaseId FROM ( " +
//				"        SELECT " +
//				"            ColumnValue, ColumnId, CaseId , " +
//				"            ROW_NUMBER() OVER(PARTITION BY ColumnId, CaseId ORDER BY Version DESC) rownumber " +
//				"        FROM KGO_CASE_DETAIL " +
//				"        where ColumnId = 'Name' " +
//				"      ) a " +
//				"    WHERE rownumber = 1 " +
//				") c on c.CaseId = m.CaseId ");
		sb.append("left join KGO_USER u2 on m.caseOfficer = u2.UserId ");
//		if (KgoRoleEnum.UNIT_U.getValue().equalsIgnoreCase(role)) {
//			sb.append("inner join KGO_CASESET_OFFICER o on o.CaseSetId = s.CaseSetId  ");
//		}
		if (KgoRoleEnum.CASE_M.getValue().equalsIgnoreCase(role)) {
			sb.append("inner join KGO_CASESET_MANAGER cm on cm.CaseSetId = s.CaseSetId  ");
		}
//		if (KgoRoleEnum.UNIT_A.getValue().equalsIgnoreCase(role)
//				|| KgoRoleEnum.UNIT_M.getValue().equalsIgnoreCase(role)) {
//			sb.append("inner join KGO_CASESET_UNIT cu on cu.CaseSetId = s.CaseSetId or m.CaseOrgan = :organId ");
//		}
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
		sb.append("GROUP BY m.CaseId, m.ApplyDate, m.ApplyUserName, m.account,s.CaseSetName,m.DeadlineDate, m.Status,u2.Name, m.processId,\n" +
				"         s.AcceptSet,s.CaseSetId,m.fDate,s.CaseFlowType,m.ApplyUserLoginType,d.Version " +
				"Order By m.CaseId desc");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), CaseMainQueryCaseViewListDto.class);
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

		return query.getResultList();
	} //caseViewSAQuery

	/**
	 *
	 * @param organId
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @param caseOfficer
	 * @return
	 */
	@Override
	public List<CaseMainQueryReviewDto> caseViewSAQueryForUpdateReviewer(String organId, String dateStart, String dateEnd, String caseSetName, String caseId, List<String> statuses, String caseOfficer,
			List<String> processIds) {

		StringBuilder sb = new StringBuilder();

		sb.append(
				"select m.CaseId CASE_ID, m.ApplyDate APPLY_DATE, c.ColumnValue APPLY_USER, s.CaseSetName CASESET_NAME, m.DeadlineDate DEADLINE_DATE, m.Status STATUS, '' TYPE, m.CaseOfficer CASE_OFFICER, u2.Name CASE_OFFICER_NAME, m.ProcessId PROCESS_ID, s.AcceptSet ACCEPT_SET, s.CaseSetId CASE_SET_ID, m.CaseOrgan ORGAN_ID ");
		sb.append("from KGO_CASE_MAIN m ");
		sb.append("left join KGO_CASESET s on m.CaseSetId=s.CaseSetId ");
		sb.append("left join ( " + "SELECT ColumnValue, ColumnId, CaseId FROM ( " + "        SELECT " + "            ColumnValue, ColumnId, CaseId , "
				+ "            ROW_NUMBER() OVER(PARTITION BY ColumnId, CaseId ORDER BY Version DESC) rownumber " + "        FROM KGO_CASE_DETAIL " + "        where ColumnId = 'Name' " + "      ) a "
				+ "    WHERE rownumber = 1 " + ") c on c.CaseId = m.CaseId ");
		sb.append("left join KGO_USER u2 on m.caseOfficer = u2.UserId ");
		sb.append("left join KGO_CASESET_UNIT cu on cu.CaseSetId = s.CaseSetId  ");
		sb.append("left join KGO_ORGAN ko on cu.Organ = ko.OrganId ");
		StringBuffer whereSb = new StringBuffer("where 1=1 ");
		if (StringUtils.isNotBlank(dateStart)) {
			whereSb.append("and convert(varchar, m.ApplyDate, 112) >= :dateStart  ");
		}
		if (StringUtils.isNotBlank(dateEnd)) {
			whereSb.append("and convert(varchar, m.ApplyDate, 112) <= :dateEnd  ");
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			whereSb.append("and s.CaseSetName like concat('%', :caseSetName, '%') ");
		}
		if (StringUtils.isNotBlank(caseId)) {
			whereSb.append("and m.CaseId = :caseId ");
		}
		if (!CollectionUtils.isEmpty(statuses)) {
			whereSb.append("and m.status in :statuses ");
		}
		if (StringUtils.isNotBlank(caseOfficer)) {
			whereSb.append("and u2.name like concat('%', :caseOfficer, '%') ");
		}
		if (StringUtils.isNotBlank(organId)) {
			whereSb.append("and (cu.Organ = :organId or ko.ParentOrganId = :organId)");
		}
		if (!CollectionUtils.isEmpty(processIds)) {
			whereSb.append("and m.ProcessId in (:processIds) ");
		}
		sb.append(whereSb);
		sb.append("order by m.UpdateTime desc");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), CaseMainQueryReviewDto.class);
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
		if (!CollectionUtils.isEmpty(statuses)) {
			query.setParameter("statuses", statuses);
		}
		if (StringUtils.isNotBlank(caseOfficer)) {
			query.setParameter("caseOfficer", caseOfficer);
		}
		if (StringUtils.isNotBlank(organId)) {
			query.setParameter("organId", organId);
		}
		if (!CollectionUtils.isEmpty(processIds)) {
			query.setParameter("processIds", processIds);
		}
		return query.getResultList();
	}

	@Override
	public List<CaseProcessSearchDto> findByUserId(String userId, String caseId, String caseSetName, String applyDateStart, String applyDateEnd, List<String> status) {
		StringBuilder sb = new StringBuilder();
		sb.append("from KGO_CASE_MAIN kcm " + "inner join KGO_CASESET kc on kcm.CaseSetId = kc.CaseSetId " + "where kcm.ApplyUser = :userId ");
		if (StringUtils.isNotBlank(caseId)) {
			sb.append("and kcm.CaseId = :caseId ");
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			sb.append("and kc.CasesetName = :caseSetName ");
		}
		if (StringUtils.isNotBlank(applyDateStart)) {
			sb.append("and kcm.ApplyDate >= :applyDateStart ");
		}
		if (StringUtils.isNotBlank(applyDateEnd)) {
			sb.append("and kcm.ApplyDate <= :applyDateEnd ");
		}
		if (!CollectionUtils.isEmpty(status)) {
			sb.append("and kcm.status in :status ");
		}

		StringBuilder columnSb = new StringBuilder();
		columnSb.append("select kcm.ApplyDate APPLY_DATE, kcm.CaseId CASE_ID, kc.CasesetName CASESET_NAME, kcm.status STATUS ");
		columnSb.append(sb);
		columnSb.append(" ORDER BY kcm.ApplyDate DESC ");

		Query query = entityManager.createNativeQuery(columnSb.toString(), CaseProcessSearchDto.class);
		query.setParameter("userId", userId);

		if (StringUtils.isNotBlank(caseId)) {
			query.setParameter("caseId", caseId);
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			query.setParameter("caseSetName", caseSetName);
		}
		if (StringUtils.isNotBlank(applyDateStart)) {
			query.setParameter("applyDateStart", applyDateStart);
		}
		if (StringUtils.isNotBlank(applyDateEnd)) {
			query.setParameter("applyDateEnd", applyDateEnd);
		}
		if (!CollectionUtils.isEmpty(status)) {
			query.setParameter("status", status);
		}
		return query.getResultList();
	}

	@Override
	public List<CaseProcessSearchDto> findByEmail(String email, String caseId, String caseSetName, String applyDateStart, String applyDateEnd, List<String> status) {
		StringBuilder sb = new StringBuilder();
		sb.append("from KGO_CASE_MAIN kcm ");
		sb.append("inner join KGO_CASESET kc on kcm.CaseSetId = kc.CaseSetId ");
		sb.append("left join ( ");
		sb.append("		select ColumnId, CaseId, max(Version) Version ");
		sb.append("		from KGO_CASE_DETAIL kcd ");
		sb.append("		where kcd.ColumnId = 'Email' ");
		sb.append("		group by ColumnId , CaseId  ");
		sb.append(") c on c.CaseId = kcm.CaseId ");
		sb.append("left join KGO_CASE_DETAIL kcd on c.ColumnId = kcd.ColumnId and c.CaseId = kcd.CaseId and c.Version = kcd.Version ");
		sb.append("where kcd.ColumnValue = :email ");
		if (StringUtils.isNotBlank(caseId)) {
			sb.append("and kcm.CaseId = :caseId ");
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			sb.append("and kc.CasesetName = :caseSetName ");
		}
		if (StringUtils.isNotBlank(applyDateStart)) {
			sb.append("and kcm.ApplyDate >= :applyDateStart ");
		}
		if (StringUtils.isNotBlank(applyDateEnd)) {
			sb.append("and kcm.ApplyDate <= :applyDateEnd ");
		}
		if (!CollectionUtils.isEmpty(status)) {
			sb.append("and kcm.status in :status ");
		}

		StringBuilder columnSb = new StringBuilder();
		columnSb.append("select kcm.ApplyDate APPLY_DATE, kcm.CaseId CASE_ID, kc.CasesetName CASESET_NAME, kcm.status STATUS ");
		columnSb.append(sb);
		columnSb.append(" ORDER BY kcm.ApplyDate DESC ");

		Query query = entityManager.createNativeQuery(columnSb.toString(), CaseProcessSearchDto.class);
		query.setParameter("email", email);

		if (StringUtils.isNotBlank(caseId)) {
			query.setParameter("caseId", caseId);
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			query.setParameter("caseSetName", caseSetName);
		}
		if (StringUtils.isNotBlank(applyDateStart)) {
			query.setParameter("applyDateStart", applyDateStart);
		}
		if (StringUtils.isNotBlank(applyDateEnd)) {
			query.setParameter("applyDateEnd", applyDateEnd);
		}
		if (!CollectionUtils.isEmpty(status)) {
			query.setParameter("status", status);
		}
		return query.getResultList();
	}

	@Override
	public List<CaseProcessSearchDto> findByUserAccount(String userAccount, String caseId, String caseSetName, String applyDateStart, String applyDateEnd, List<String> status) {
		StringBuilder sb = new StringBuilder();
		sb.append("from KGO_CASE_MAIN kcm " + "inner join KGO_CASESET kc on kcm.CaseSetId = kc.CaseSetId " + "where kcm.Account = :account ");
		if (StringUtils.isNotBlank(caseId)) {
			sb.append("and kcm.CaseId = :caseId ");
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			sb.append("and kc.CasesetName = :caseSetName ");
		}
		if (StringUtils.isNotBlank(applyDateStart)) {
			sb.append("and kcm.ApplyDate >= :applyDateStart ");
		}
		if (StringUtils.isNotBlank(applyDateEnd)) {
			sb.append("and kcm.ApplyDate <= :applyDateEnd ");
		}
		if (!CollectionUtils.isEmpty(status)) {
			sb.append("and kcm.status in :status ");
		}

		StringBuilder columnSb = new StringBuilder();
		columnSb.append("select kcm.ApplyDate APPLY_DATE, kcm.CaseId CASE_ID, kc.CasesetName CASESET_NAME, kcm.status STATUS ");
		columnSb.append(sb);
		columnSb.append(" ORDER BY kcm.ApplyDate DESC ");

		Query query = entityManager.createNativeQuery(columnSb.toString(), CaseProcessSearchDto.class);
		query.setParameter("account", userAccount);

		if (StringUtils.isNotBlank(caseId)) {
			query.setParameter("caseId", caseId);
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			query.setParameter("caseSetName", caseSetName);
		}
		if (StringUtils.isNotBlank(applyDateStart)) {
			query.setParameter("applyDateStart", applyDateStart);
		}
		if (StringUtils.isNotBlank(applyDateEnd)) {
			query.setParameter("applyDateEnd", applyDateEnd);
		}
		if (!CollectionUtils.isEmpty(status)) {
			query.setParameter("status", status);
		}
		return query.getResultList();
	}

	/**
	 * [待簽收匣]
	 *
	 * 查詢該登入者身分底下所擁有的[待簽收] 案件
	 *
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @return
	 */
	@Override
	public List<CaseMainQueryDto> pendingSignSAQuery(List<String> processIds, String userName, String dateStart, String dateEnd, String caseSetName, String caseId) {

		StringBuilder sb = new StringBuilder();

		/** GEO 20211102 add 申請人登入方式 */
		sb.append("select m.CaseId CASE_ID, m.ApplyDate APPLY_DATE, m.ApplyUserName APPLY_USER, s.CaseSetName CASESET_NAME, m.DeadlineDate DEADLINE_DATE, "
				+ "s.CaseType TYPE, m.Status STATUS, m.CaseOfficer CASE_OFFICER, " +
				"m.ProcessId  PROCESS_ID, s.AcceptSet ACCEPT_SET, s.CaseSetId CASE_SET_ID, m.ApplyUserLoginType APPLY_USER_LOGIN_TYPE ");
		sb.append("from KGO_CASE_MAIN m left join KGO_CASESET s on m.CaseSetId=s.CaseSetId ");
		sb.append(getSACommonWhereStr(processIds, userName, dateStart, dateEnd, caseSetName, caseId));
		sb.append("and m.Status in ('A3','W3') ");
		sb.append(COMMON_SA_ORDER_STR);

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), CaseMainQueryDto.class);
		setSAQueryCommonParameters(query, processIds, userName, dateStart, dateEnd, caseSetName, caseId);

		return query.getResultList();
	}

	/**
	 * [待簽收匣]
	 *
	 * 簽收
	 *
	 * @param caseId
	 * @return
	 */
	@Override
	public void pendingSignSAAccept(String caseId) {

		StringBuilder sb = new StringBuilder();
		String status = CaseMainStatusEnum.P3.getValue();

		sb.append("UPDATE KGO_CASE_MAIN m SET m.Status= :status WHERE m.CaseId = :caseId");

		/**
		 * Native Query
		 */
		entityManager.createNativeQuery(sb.toString()).setParameter("status", status).setParameter("caseId", caseId).executeUpdate();
	}

	/**
	 * [待審核匣]
	 *
	 * 查詢該登入者身分底下所擁有的[待審核] 案件
	 *
	 * @param processIds
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @return
	 */
	@Override
	public List<PendingReviewCaseMainQueryDto> pendingReviewSAQuery(List<String> processIds, String userName, String dateStart, String dateEnd, String caseSetName, String caseId) {
		/** GEO 20211102 add 申請人登入方式 */
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ( ");
		sb.append("select m.CaseId CASE_ID, m.ApplyDate APPLY_DATE, m.ApplyUserName APPLY_USER, s.CaseSetName CASESET_NAME, "
				+ "m.DeadlineDate DEADLINE_DATE, s.CaseType TYPE, m.Status STATUS, s.AcceptSet ACCEPT_SET, " +
				"m.ProcessId PROCESS_ID, m.UpdateTime UPDATE_TIME, m.ApplyUserLoginType APPLY_USER_LOGIN_TYPE ");
		sb.append("from KGO_CASE_MAIN m left join KGO_CASESET s on m.CaseSetId=s.CaseSetId ");
		sb.append("where 1=1 ");
		if (!processIds.isEmpty()) {
			sb.append("and m.processId in (:processIds) ");
		}
		if (StringUtils.isNotBlank(userName)) {
			sb.append("and m.ApplyUserName like concat('%', :userName, '%') ");
		}
		if (StringUtils.isNotBlank(dateStart)) {
			sb.append("and convert(varchar, m.ApplyDate, 112) >= :dateStart ");
		}
		if (StringUtils.isNotBlank(dateEnd)) {
			sb.append("and convert(varchar, m.ApplyDate, 112) <= :dateEnd ");
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			sb.append("and s.CaseSetName like concat('%', :caseSetName, '%') ");
		}
		if (StringUtils.isNotBlank(caseId)) {
			sb.append("and m.CaseId = :caseId ");
		}
		/** GEO 20211026 add */
		/** GEO 20211102 add 申請人登入方式 */
		sb.append("and m.Status in ('P3','C3','P','13') ");
		sb.append("union all ");
		sb.append("select ksa.CaseId CASE_ID, ksa.CreateTime APPLY_DATE, u.name APPLY_USER, '系統權限申請' CASESET_NAME, "
				+ "'' DEADLINE_DATE, 'URA' TYPE, ksa.Status STATUS, '' ACCEPT_SET, ksa.ProcessId PROCESS_ID, ksa.UpdateTime UPDATE_TIME, '' APPLY_USER_LOGIN_TYPE ");
		sb.append("from KGO_URA_APPLY ksa ");
		sb.append("inner join KGO_USER u on ksa.ApplyUser = u.UserId ");
		if (!processIds.isEmpty()) {
			sb.append("and ksa.processId in (:processIds) ");
		}
		if (StringUtils.isNotBlank(userName)) {
			sb.append("and u.name like concat('%', :userName, '%') ");
		}
		if (StringUtils.isNotBlank(dateStart)) {
			sb.append("and convert(varchar, ksa.CreateTime, 112) >= :dateStart ");
		}
		if (StringUtils.isNotBlank(dateEnd)) {
			sb.append("and convert(varchar, ksa.CreateTime, 112) <= :dateEnd ");
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			sb.append("and '系統權限申請' like concat('%', :caseSetName, '%') ");
		}
		if (StringUtils.isNotBlank(caseId)) {
			sb.append("and ksa.CaseId = :caseId ");
		}
		sb.append(") a order by UPDATE_TIME desc");
		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), PendingReviewCaseMainQueryDto.class);
		if (!processIds.isEmpty()) {
			query.setParameter("processIds", processIds);
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

		return query.getResultList();
	}

	/**
	 * [已審核匣]
	 *
	 * 查詢該登入者身分底下所擁有的[已審核] SA案件
	 *
	 * @param processIds
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @return
	 */
	@Override
	public List<CaseMainQueryReviewedDto> reviewedSAQuery(List<String> processIds, String userName, String dateStart, String dateEnd, String caseSetName, String caseId) {
		/** GEO 20211102 add 申請人登入方式 */
		StringBuilder sb = new StringBuilder();
		sb.append(
				"select * from " +
						"( select " +
						"    m.CaseId CASE_ID, " +
						"    m.ApplyDate APPLY_DATE, " +
						"    m.ApplyUserName APPLY_USER, " +
						"    s.CaseSetName CASESET_NAME, " +
						"    m.DeadlineDate DEADLINE_DATE, " +
						"    m.Status STATUS, " +
						"    s.CaseType TYPE, " +
						"    m.CaseOfficer CASE_OFFICER, " +
						"    m.ProcessId PROCESS_ID, " +
						"    s.AcceptSet ACCEPT_SET, " +
						"    s.CaseSetId CASE_SET_ID, " +
						"    m.fdate FDATE, " +
						"    m.UpdateTime UPDATE_TIME, " +
						"    m.ApplyUserLoginType APPLY_USER_LOGIN_TYPE " +
						"  from KGO_CASE_MAIN m left join KGO_CASESET s on m.CaseSetId=s.CaseSetId " +
						"  union all " +
						"  select " +
						"    ksa.CaseId CASE_ID, " +
						"    ksa.CreateTime APPLY_DATE, " +
						"    u.name APPLY_USER, " +
						"    '系統權限申請' CASESET_NAME, " +
						"    '' DEADLINE_DATE, " +
						"    ksa.Status STATUS, " +
						"    'URA' TYPE, " +
						"    '' CASE_OFFICER, " +
						"    ksa.ProcessId PROCESS_ID, " +
						"    '' ACCEPT_SET, " +
						"    '' CASE_SET_ID, " +
						"    '' FDATE, " +
						"    ksa.UpdateTime UPDATE_TIME, " +
						"    '' APPLY_USER_LOGIN_TYPE " +
						"  from KGO_URA_APPLY ksa " +
						"  inner join KGO_USER u on ksa.ApplyUser = u.UserId ) a " +
						"where a.Status in ('F3', 'J3', 'S3') "
		);

		if (!processIds.isEmpty()) {
			sb.append("and a.PROCESS_ID in (:processIds) ");
		}
		if (StringUtils.isNotBlank(userName)) {
			sb.append("and a.APPLY_USER like concat('%', :userName, '%') ");
		}
		if (StringUtils.isNotBlank(dateStart)) {
			sb.append("and convert(varchar, a.APPLY_DATE, 112) >= :dateStart ");
		}
		if (StringUtils.isNotBlank(dateEnd)) {
			sb.append("and convert(varchar, a.APPLY_DATE, 112) <= :dateEnd ");
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			sb.append("and a.CASESET_NAME like concat('%', :caseSetName, '%') ");
		}
		if (StringUtils.isNotBlank(caseId)) {
			sb.append("and a.CASE_ID = :caseId ");
		}

		sb.append("order by UPDATE_TIME desc");

//		sb.append("and m.Status in ('F3', 'J3', 'S3') ");
//		sb.append("union all ");
//		sb.append("select ksa.CaseId CASE_ID, ksa.CreateTime APPLY_DATE, u.name APPLY_USER, '系統權限申請' CASESET_NAME, '' DEADLINE_DATE, "
//				+ "ksa.Status STATUS, 'URA' TYPE, '' CASE_OFFICER, ksa.ProcessId PROCESS_ID, '' ACCEPT_SET, '' CASE_SET_ID, '' FDATE, ksa.UpdateTime UPDATE_TIME, '' APPLY_USER_LOGIN_TYPE ");
//		sb.append("from KGO_URA_APPLY ksa ");
//		sb.append("inner join KGO_USER u on ksa.ApplyUser = u.UserId ");
//		if (!processIds.isEmpty()) {
//			sb.append("and ksa.processId in (:processIds) ");
//		}
//		if (StringUtils.isNotBlank(userName)) {
//			sb.append("and u.name like concat('%', :userName, '%') ");
//		}
//		if (StringUtils.isNotBlank(dateStart)) {
//			sb.append("and convert(varchar, ksa.CreateTime, 112) >= :dateStart ");
//		}
//		if (StringUtils.isNotBlank(dateEnd)) {
//			sb.append("and convert(varchar, ksa.CreateTime, 112) <= :dateEnd ");
//		}
//		if (StringUtils.isNotBlank(caseSetName)) {
//			sb.append("and '系統權限申請' like concat('%', :caseSetName, '%') ");
//		}
//		if (StringUtils.isNotBlank(caseId)) {
//			sb.append("and ksa.CaseId = :caseId ");
//		}

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), CaseMainQueryReviewedDto.class);
		setSAQueryCommonParameters(query, processIds, userName, dateStart, dateEnd, caseSetName, caseId);

		return query.getResultList();
	}

	/**
	 * 根據畫面上所輸入的條件來產生SA案件查詢時 where部分的字串
	 *
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @param status
	 * @return
	 */
	private StringBuffer getSACommonWhereStr(String userName, String dateStart, String dateEnd, String caseSetName, String caseId, String status) {
		StringBuffer sb = new StringBuffer("where 1=1 ");
		if (StringUtils.isNotBlank(userName)) {
			sb.append("and u.Name like concat('%', :userName, '%') ");
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
			sb.append("and m.CaseId = :caseId ");
		}
		if (StringUtils.isNotBlank(status)) {
			sb.append("and m.status = :status ");
		}
		return sb;
	}

	/**
	 * 根據畫面上所輸入的條件來設定 SA案件查詢時所需的參數值
	 *
	 * @param query
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @param status
	 */
	private void setSAQueryCommonParameters(Query query, String userName, String dateStart, String dateEnd, String caseSetName, String caseId, String status) {
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
		if (StringUtils.isNotBlank(status)) {
			query.setParameter("status", status);
		}
	}

	/**
	 * 根據畫面上所輸入的條件來產生SA案件查詢時 where部分的字串
	 *
	 * @param processIds
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @return
	 */
	private StringBuffer getSACommonWhereStr(List<String> processIds, String userName, String dateStart, String dateEnd, String caseSetName, String caseId) {
		StringBuffer sb = new StringBuffer("where 1=1 ");
		if (!processIds.isEmpty()) {
			sb.append("and m.processId in (:processIds) ");
		}
		if (StringUtils.isNotBlank(userName)) {
			sb.append("and m.ApplyUserName like concat('%', :userName, '%') ");
		}
		if (StringUtils.isNotBlank(dateStart)) {
			sb.append("and convert(varchar, m.ApplyDate, 112) >= :dateStart ");
		}
		if (StringUtils.isNotBlank(dateEnd)) {
			sb.append("and convert(varchar, m.ApplyDate, 112) <= :dateEnd ");
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			sb.append("and s.CaseSetName like concat('%', :caseSetName, '%') ");
		}
		if (StringUtils.isNotBlank(caseId)) {
			sb.append("and m.CaseId = :caseId ");
		}
		return sb;
	}

	/**
	 * 根據畫面上所輸入的條件來設定 SA案件查詢時所需的參數值
	 *
	 * @param query
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 */
	private void setSAQueryCommonParameters(Query query, List<String> processIds, String userName, String dateStart, String dateEnd, String caseSetName, String caseId) {
		if (!processIds.isEmpty()) {
			query.setParameter("processIds", processIds);
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
	}

	/**
	 * 進案時間 前兩名dto.
	 *
	 * @param startDate the start date
	 * @param endDate   the end date
	 * @return the list
	 */
	@Override
	public List<CaseMainCaseSetIdCountDto> queryTop2CaseSetIdByDate(Timestamp startDate, Timestamp endDate, String organId) {

		QueryParamSql querySql = this.queryTop2CaseSetIdByDateSql(startDate, endDate, organId);
		// 取得查詢SQL物件
		String sql = querySql.getSql();
		Map<String, Object> param = querySql.getParamMap();

		Query query = entityManager.createNativeQuery(sql, CaseMainCaseSetIdCountDto.class);

		for (Entry<String, Object> entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		List<CaseMainCaseSetIdCountDto> queryList = query.getResultList();
		return queryList;
	}

	/**
	 * 進案時間 前兩名dto SQL.
	 *
	 * @param startDate the start date
	 * @param endDate   the end date
	 * @return the query param sql
	 */
	private QueryParamSql queryTop2CaseSetIdByDateSql(Timestamp startDate, Timestamp endDate, String organId) {
		StringBuilder sb = new StringBuilder();
		QueryParamSql querySql = new QueryParamSql();

		sb.append(" Select top 2 m.CaseSetId, Count(m.CaseSetId) Count from KGO_CASE_MAIN m ");
		sb.append(" Left join KGO_CASESET s on m.CaseSetId=s.CaseSetId ");
		sb.append(" where m.UpdateTime >=:startDate and m.UpdateTime <=:endDate  ");

		if (StringUtils.isNotBlank(organId)) {
			sb.append(" and m.CaseOrgan = :organId ");
			querySql.addParam("organId", organId);
		}

		sb.append(" GROUP by m.CaseSetId order by Count desc;");

		querySql.addParam("startDate", startDate);
		querySql.addParam("endDate", endDate);

		querySql.setSql(sb.toString());
		return querySql;
	}

	/**
	 * 案件軌跡紀錄 - 承辦人受理案件數.
	 *
	 * @param systemType   the system type
	 * @param functionCode the function code
	 * @param startDate    the start date
	 * @param endDate      the end date
	 * @return the int
	 */
	public List<CaseMainCaseSetCountDto> queryCaseSetCount(Timestamp startDate, Timestamp endDate, String organId) {
		StringBuilder sb = new StringBuilder();
		sb.append("Select new gov.kcg.kgo.dto.CaseMainCaseSetCountDto(m.caseSetId ,s.caseSetName, Count(s.caseSetId) as Count) ");
		sb.append("	from ");
		sb.append("		KgoCaseMain m ");
		sb.append(" Left join ");
		sb.append("     KgoCaseset s on m.caseSetId = s.caseSetId  ");
		sb.append(" where ");
		sb.append("     m.createTime>=:startDate and m.createTime<=:endDate ");

		if (StringUtils.isNotEmpty(organId)) {
			sb.append(" and m.caseOrgan = :organId ");
		}

		sb.append("	group by ");
		sb.append("		m.caseSetId, s.caseSetName Order by Count(s.caseSetName) desc ");

		Query query = entityManager.createQuery(sb.toString());
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);

		if (StringUtils.isNotEmpty(organId)) {
			query.setParameter("organId", organId);
		}

		List<CaseMainCaseSetCountDto> queryList = query.getResultList();
		return queryList;
	}

	/**
	 * 案件狀態統計分析Top5 (isLate:是否逾期).
	 *
	 * @param startDate the start date
	 * @param endDate   the end date
	 * @return the list
	 */
	@Override
	public List<CaseMainCaseStatusIsLateCountDto> queryTop5CaseStatusIsLateByDate(Timestamp startDate, Timestamp endDate, Boolean isLate, String organId) {
		QueryParamSql querySql = this.queryTop5CaseStatusIsLateByDateSql(startDate, endDate, isLate, organId); // 取得查詢SQL物件
		String sql = querySql.getSql();
		Map<String, Object> param = querySql.getParamMap();

		Query query = entityManager.createNativeQuery(sql, CaseMainCaseStatusIsLateCountDto.class);

		for (Entry<String, Object> entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		List<CaseMainCaseStatusIsLateCountDto> queryList = query.getResultList();
		return queryList;
	}

	/**
	 * 案件狀態統計分析 (isLate:是否逾期) SQL.
	 *
	 * @param startDate the start date
	 * @param endDate   the end date
	 * @return the query param sql
	 */
	private QueryParamSql queryTop5CaseStatusIsLateByDateSql(Timestamp startDate, Timestamp endDate, Boolean isLate, String organId) {
		StringBuilder sb = new StringBuilder();
		QueryParamSql querySql = new QueryParamSql();

		sb.append("Select top 5 m.status, Count(m.status) Count from KGO_CASE_MAIN m ");
		sb.append("Left join KGO_CASESET s on m.CaseSetId=s.CaseSetId ");
		sb.append("where m.UpdateTime >=:startDate and m.UpdateTime<=:endDate ");

		if (StringUtils.isNotBlank(organId)) {
			sb.append(" and m.CaseOrgan = :organId ");
			querySql.addParam("organId", organId);
		}

		// 是否逾期
		if (isLate != null) {
			sb.append(" and m.IsLate = :isLate ");
			querySql.addParam("isLate", isLate);
		}

		sb.append(" GROUP by m.status order by Count desc ");

		querySql.addParam("startDate", startDate);
		querySql.addParam("endDate", endDate);
		querySql.setSql(sb.toString());
		return querySql;
	}

	/**
	 * 案件狀態統計分析Top5 (isLate:是否逾期).
	 *
	 * @param startDate the start date
	 * @param endDate   the end date
	 * @return the list
	 */
	@Override
	public List<CaseMainCaseStatusCountDto> queryTop10CaseStatusByDate(Timestamp startDate, Timestamp endDate, List<String> statusList, String organId) {

		QueryParamSql querySql = this.queryTop10CaseStatusByDateSql(startDate, endDate, statusList, organId);
		// 取得查詢SQL物件
		String sql = querySql.getSql();
		Map<String, Object> param = querySql.getParamMap();

		Query query = entityManager.createNativeQuery(sql, CaseMainCaseStatusCountDto.class);

		for (Entry<String, Object> entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		List<CaseMainCaseStatusCountDto> queryList = query.getResultList();
		return queryList;
	}

	/**
	 * 案件狀態統計分析 SQL.
	 *
	 * @param startDate the start date
	 * @param endDate   the end date
	 * @return the query param sql
	 */
	private QueryParamSql queryTop10CaseStatusByDateSql(Timestamp startDate, Timestamp endDate, List<String> statusList, String organId) {
		StringBuilder sb = new StringBuilder();
		QueryParamSql querySql = new QueryParamSql();

		sb.append(" Select top 10 Convert(date, m.UpdateTime,111) UpdateDate, Count(m.status) Count ");
		sb.append(" from KGO_CASE_MAIN m ");
		sb.append(" Left join KGO_CASESET s on m.CaseSetId=s.CaseSetId ");
		sb.append(" where m.UpdateTime is not null ");
		sb.append(" and m.Status in :statusList ");
		sb.append(" and m.CreateTime >=:startDate and m.CreateTime<=:endDate ");

		if (StringUtils.isNotBlank(organId)) {
			sb.append(" and m.CaseOrgan = :organId ");
			querySql.addParam("organId", organId);
		}

		sb.append(" GROUP by Convert(date, m.UpdateTime,111) order by Count desc ");

		querySql.addParam("startDate", startDate);
		querySql.addParam("endDate", endDate);
		querySql.addParam("statusList", statusList);
		querySql.setSql(sb.toString());
		return querySql;
	}

	/**
	 * 動態查詢 by查詢條件.
	 *
	 * @param condition the condition
	 * @return the list
	 */
	@Override
	public List<KgoCaseMain> findByCondition(KgoCaseMainQueryCondition condition) {
		QueryParamSql querySql = this.findByConditionSql(condition);
		// 取得查詢SQL物件
		String sql = querySql.getSql();
		Map<String, Object> param = querySql.getParamMap();

		Query query = entityManager.createQuery(sql);

		for (Entry<String, Object> entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		List<KgoCaseMain> queryList = query.getResultList();
		return queryList;
	}

	private QueryParamSql findByConditionSql(KgoCaseMainQueryCondition condition) {
		StringBuilder sb = new StringBuilder();
		QueryParamSql querySql = new QueryParamSql();

		sb.append("FROM KgoCaseMain m ");

		// 案件編號
		if (StringUtils.isNotBlank(condition.getCaseId())) {
			sb.append(" and m.caseId = :caseId");
			querySql.addParam("caseId", condition.getCaseId());
		}
		// 申請者 (身分證號)
		if (StringUtils.isNotBlank(condition.getApplyUser())) {
			sb.append(" and m.applyUser = :applyUser");
			querySql.addParam("applyUser", condition.getApplyUser());
		}
		// 手機
		if (StringUtils.isNotBlank(condition.getCellPhone())) {
			sb.append(" and m.cellPhone = :cellPhone");
			querySql.addParam("cellPhone", condition.getCellPhone());
		}

		// 建立日期 (起)
		if (condition.getStartDate() != null) {
			sb.append(" and m.createTime >= :startDate");
			querySql.addParam("startDate", condition.getStartDate());
		}

		// 建立日期 (迄)
		if (condition.getEndDate() != null) {
			sb.append(" and m.createTime <= :endDate");
			querySql.addParam("endDate", condition.getEndDate());
		}

		// 機關
		if (StringUtils.isNotBlank(condition.getOrganId())) {
			sb.append(" and m.caseOrgan = :organId");
			querySql.addParam("organId", condition.getOrganId());
		}

		// 需動態查詢欄位 再往下加

		// 將最前面的 and 置換為 where
		String sql = StringUtils.replaceOnce(sb.toString(), "and", "where");
		querySql.setSql(sql);
		return querySql;
	}

	/**
	 * GEO 20211019 add
	 */
	@Override
	public List<String> findCaseIdCheckFrequencyColumn(String columnId, String columnValue, Integer version, String caseId, Timestamp time, Timestamp checkTime, String cColumnId) {
		String selectStr = "SELECT cd.ColumnId, cd.ColumnValue,cd.CaseId ";
		String fromStr = "FROM  KGO_CASE_DETAIL cd, KGO_CASE_MAIN cm ";
		String whereStr = "where cm.CaseId = cd.CaseId ";
		if (StringUtils.isNotBlank(columnId)) whereStr += "and cd.ColumnId = :columnId ";
		if (StringUtils.isNotBlank(columnValue)) whereStr += "and cd.ColumnValue = :columnValue ";
		if (version != null) whereStr += "and cd.Version = :version ";
		if (StringUtils.isNotBlank(caseId)) whereStr += "and cd.CaseId = :caseId ";
		if (checkTime != null) whereStr += "and cm.ApplyDate > Convert(datetime, :checkTime) ";
		if (time != null) whereStr += "and cm.ApplyDate < Convert(datetime, :time) ";
		if (StringUtils.isNotBlank(cColumnId)) whereStr += "and cd.CColumnId = :cColumnId ";

		Query sqlQuery = entityManager.createNativeQuery(selectStr + fromStr + whereStr );
		if (StringUtils.isNotBlank(columnId)) sqlQuery.setParameter("columnId", columnId);
		if (StringUtils.isNotBlank(columnValue)) sqlQuery.setParameter("columnValue", columnValue);
		if (version != null) sqlQuery.setParameter("version", version);
		if (StringUtils.isNotBlank(caseId)) sqlQuery.setParameter("caseId", caseId);
		if (checkTime != null) sqlQuery.setParameter("checkTime", checkTime);
		if (time != null) sqlQuery.setParameter("time", time);
		if (StringUtils.isNotBlank(cColumnId)) sqlQuery.setParameter("cColumnId", cColumnId);
		List listData = sqlQuery.getResultList();
		List<String> datas = Collections.synchronizedList(new ArrayList<>());
		for (int i = 0; i < listData.size(); i++) {
			Object[] recordArray = (Object[]) listData.get(i);
			String model = (String) recordArray[2];
			datas.add(model);
		}

		LOGGER.info(String.format("columnId:%s, columnValue:%s, version:%s, caseId:%s, time:%s, checkTime:%s "
				,columnId,columnValue, String.valueOf(version),caseId,
				DateUtil.timestampToString(time, DateUtil.PATTEN_FULL_TIME_MS),
				DateUtil.timestampToString(checkTime, DateUtil.PATTEN_FULL_TIME_MS)));
		LOGGER.info("check columnId listData.size: " + listData.size());
		if (datas.size() > 0) {
			for (String s : datas) {
				LOGGER.info("check columnId : caseId: " + s);
			}
		}
		return datas;
	} //findByCheckFrequencyColumn

	/**
	 * GEO 20211019 add
	 */
	@Override
	public List<CaseMainCheckFrequencyDto> findCheckColumn(String caseSetId, Integer isCheckFrequency, Integer version) {
		String selectStr = "SELECT cc.ColumnId as ColumnId, cg.CheckFrequencyPeriod as CheckFrequencyPeriod, ccc.CColumnId as CColumnId ";
		String fromStr = "FROM KGO_CASESET_GROUP cg " +
				"left join KGO_CASESET_COLUMN cc on cg.GroupSeq = cc.GroupSeq " +
				"left join KGO_CASESET_COLUMN_CHILD ccc on ccc.ColumnId = cc.ColumnId " +
				"and ccc.Version = cg.Version and ccc.CaseSetId = cg.CaseSetId " +
				",KGO_CASESET c ";
		String whereStr = "WHERE c.CaseSetId = cg.CaseSetId and cg.CheckFrequencyPeriod is not null ";
		if (StringUtils.isNotBlank(caseSetId)) whereStr += "and c.CaseSetId = :caseSetId ";
		if (isCheckFrequency != null) whereStr += "and cc.IsCheckFrequency = :isCheckFrequency ";
		if (version != null) whereStr += "and cg.Version = :version ";

		Query sqlQuery = entityManager.createNativeQuery(selectStr + fromStr + whereStr);
		if (StringUtils.isNotBlank(caseSetId)) sqlQuery.setParameter("caseSetId", caseSetId);
		if (isCheckFrequency != null) sqlQuery.setParameter("isCheckFrequency", isCheckFrequency);
		if (version != null) sqlQuery.setParameter("version", version);
		List listData = sqlQuery.getResultList();
		List<CaseMainCheckFrequencyDto> datas = Collections.synchronizedList(new ArrayList<>());
		if (listData != null && listData.size() > 0) {
			for (int i = 0; i < listData.size(); i++) {
				CaseMainCheckFrequencyDto model = new CaseMainCheckFrequencyDto();
				Object[] recordArray = (Object[]) listData.get(i);
				model.setColumnId((String) recordArray[0]);
				model.setCheckFrequencyPeriod((String) recordArray[1]);
				model.setCColumnId((String) recordArray[2]);
				datas.add(model);
			}
		}
		LOGGER.info(String.format("caseSetId:%s, isCheckFrequency:%s, version:%s ",caseSetId,String.valueOf(isCheckFrequency),String.valueOf(version)));
		LOGGER.info("listData size: " + datas.size());
		if (listData.size() > 0) {
			for (CaseMainCheckFrequencyDto s : datas) {
				LOGGER.info("listData: ColumnId/CColumnId: " + s.getColumnId() + " / " + s.getCColumnId());
			}
		}
		return datas;
	} //findCheckColumn

	/**
	 * GEO 20211115 add for 跨機關檢視
	 * 後台案件處理-跨機關檢視-案件查詢
	 * @param role
	 * @param loginUserId
	 * @param organId
	 * @param userName
	 * @param dateStart
	 * @param dateEnd
	 * @param caseSetName
	 * @param caseId
	 * @param status
	 * @param caseFlowType
	 * @param id
	 * @param cellPhone
	 * @return
	 */
	@Override
	public List<CaseMainQueryCaseViewListDto> CrossReviewQuery(String role, String loginUserId, String organId, String userName, String dateStart, String dateEnd, String caseSetName, String caseId,
															  List<String> status, String caseFlowType, String id, String cellPhone) {
		String withSelectStr = "WITH CROSS_REVIEW (CaseSetId, OrganId) AS " +
				"( SELECT cs.CaseSetId, cs.OrganId FROM GEO_KGO_CASESET_CROSS_REVIEW  cs where OrganId = :OrganId ) ";
		String selectSTr = "select m.CaseId CASE_ID, m.ApplyDate APPLY_DATE, ISNULL(m.ApplyUserName, m.account) APPLY_USER, s.CaseSetName CASESET_NAME, " +
				"m.DeadlineDate DEADLINE_DATE, m.Status STATUS, '' TYPE, u2.Name CASE_OFFICER, m.processId PROCESS_ID, s.AcceptSet ACCEPT_SET, " +
				"s.CaseSetId CASE_SET_ID, m.fDate FDATE, s.CaseFlowType CASE_FLOW_TYPE, m.ApplyUserLoginType APPLY_USER_LOGIN_TYPE, d.Version VERSION ";
		String fromStr = "from CROSS_REVIEW c,  KGO_USER u,KGO_CASE_MAIN m  " +
				"left join KGO_CASE_DETAIL d on m.CaseId=d.CaseId " +
				"left join KGO_CASESET s on m.CaseSetId=s.CaseSetId " +
				"left join KGO_USER u2 on m.caseOfficer = u2.UserId " +
				"left join GEO_KGO_CASESET_CROSS_REVIEW cr on cr.CaseSetId = m.CaseSetId ";
		String whereStr = "where s.IsAvailableCrossReview = 1 AND c.CaseSetId = m.CaseSetId AND u.UserId = :userId AND u.IsAvailableCrossReview = 1 ";
		String orderStr = "GROUP BY m.CaseId, m.ApplyDate, m.ApplyUserName, m.account,s.CaseSetName,m.DeadlineDate, m.Status,u2.Name, m.processId,\n" +
				"         s.AcceptSet,s.CaseSetId,m.fDate,s.CaseFlowType,m.ApplyUserLoginType,d.Version\n" +
				"Order By m.CaseId desc ";
		if (StringUtils.isNotBlank(userName)) whereStr += "and m.ApplyUserName like concat('%', :userName, '%') ";
		if (StringUtils.isNotBlank(dateStart)) whereStr += "and convert(varchar, m.ApplyDate, 112) >= :dateStart  ";
		if (StringUtils.isNotBlank(dateEnd)) whereStr += "and convert(varchar, m.ApplyDate, 112) <= :dateEnd  ";
		if (StringUtils.isNotBlank(caseSetName)) whereStr += "and s.CaseSetName like concat('%', :caseSetName, '%') ";
		if (StringUtils.isNotBlank(caseId)) whereStr += "and :caseId in (m.CaseId,m.OCaseId) ";
		if (StringUtils.isNotBlank(caseFlowType)) whereStr += "and s.CaseFlowType = :caseFlowType ";
		if (!CollectionUtils.isEmpty(status)) whereStr += "and m.status in :status ";
		if (StringUtils.isNotBlank(id)) whereStr += "and m.ApplyUser = :id ";
		if (StringUtils.isNotBlank(cellPhone)) whereStr += "and m.CellPhone = :cellPhone ";

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(withSelectStr + selectSTr + fromStr + whereStr + orderStr, CaseMainQueryCaseViewListDto.class);
		query.setParameter("OrganId", KgoUtil.getBackendLoginUser().getOrgan());
		query.setParameter("userId", KgoUtil.getBackendLoginUser().getUserId());
		if (StringUtils.isNotBlank(userName)) query.setParameter("userName", userName);
		if (StringUtils.isNotBlank(dateStart)) query.setParameter("dateStart", dateStart);
		if (StringUtils.isNotBlank(dateEnd)) query.setParameter("dateEnd", dateEnd);
		if (StringUtils.isNotBlank(caseSetName)) query.setParameter("caseSetName", caseSetName);
		if (StringUtils.isNotBlank(caseId)) query.setParameter("caseId", caseId);
		if (!CollectionUtils.isEmpty(status)) query.setParameter("status", status);
		if (StringUtils.isNotBlank(caseFlowType)) query.setParameter("caseFlowType", caseFlowType);
		if (StringUtils.isNotBlank(id)) query.setParameter("id", id);
		if (StringUtils.isNotBlank(cellPhone)) query.setParameter("cellPhone", cellPhone);
		LOGGER.info("CrossReviewQuery query : " + withSelectStr + selectSTr + fromStr + whereStr + orderStr);
		LOGGER.info("CrossReviewQuery query.getResultList().size() : " + query.getResultList().size());

		return query.getResultList();
	} //caseViewSAQuery

	/**
	 ** 案件查詢 - 好孕行得通
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CaseMainQueryCaseViewListDto> caseByOrganQuery(String loginUserId, String organId, String userName, String dateStart, String dateEnd, String caseSetName, String caseId,
			List<String> status, String caseFlowType, String id, String cellPhone, String caseSet, String version) {
		LOGGER.info("caseByOrganQuery loginUserId="+loginUserId+",organId="+organId);
		LOGGER.info("caseByOrganQuery userName="+userName+",dateStart="+dateStart+",dateEnd="+dateEnd);
		LOGGER.info("caseByOrganQuery caseSetName="+caseSetName+",caseId="+caseId+",id="+id);
		LOGGER.info("caseByOrganQuery cellPhone="+cellPhone+",caseSet="+caseSet+",version="+version);
		StringBuilder sb = new StringBuilder();
		sb.append("select m.CaseId CASE_ID,"+
		          "m.ApplyDate APPLY_DATE,"+
				  "ISNULL(m.ApplyUserName, m.account) APPLY_USER,"+
		          "s.CaseSetName CASESET_NAME,"+
				  "m.DeadlineDate DEADLINE_DATE,"+
		          "m.Status STATUS,"+
				  "'' TYPE,"+
		          "u2.Name CASE_OFFICER,"+
				  "m.processId PROCESS_ID,"+
		          "s.AcceptSet ACCEPT_SET,"+
				  "s.CaseSetId CASE_SET_ID,"+
		          "m.fDate FDATE,"+
				  "s.CaseFlowType CASE_FLOW_TYPE,"+
		          "m.ApplyUserLoginType APPLY_USER_LOGIN_TYPE,"+
				  "d.Version VERSION");
		sb.append(" from KGO_CASE_MAIN m");
		sb.append(" left join KGO_CASE_DETAIL d on m.CaseId=d.CaseId");
		sb.append(" left join KGO_CASESET s on m.CaseSetId=s.CaseSetId");
		sb.append(" left join KGO_USER u2 on m.caseOfficer = u2.UserId");
		sb.append(" where 1=1");

		if (StringUtils.isNotBlank(userName)) {
			sb.append(" and m.ApplyUserName like concat('%', :userName, '%')");
		}
		if (StringUtils.isNotBlank(dateStart)) {
			sb.append(" and convert(varchar, m.ApplyDate, 112) >= :dateStart");
		}
		if (StringUtils.isNotBlank(dateEnd)) {
			sb.append(" and convert(varchar, m.ApplyDate, 112) <= :dateEnd");
		}
		if (StringUtils.isNotBlank(caseSetName)) {
			sb.append(" and s.CaseSetName like concat('%', :caseSetName, '%')");
		}
		if (StringUtils.isNotBlank(caseId)) {
			sb.append(" and :caseId in (m.CaseId,m.OCaseId)");
		}
		if (StringUtils.isNotBlank(caseFlowType)) {
			sb.append(" and s.CaseFlowType = :caseFlowType");
		}
		if (!CollectionUtils.isEmpty(status)) {
			sb.append(" and m.status in :status");
		}
		if (StringUtils.isNotBlank(id)) {
			sb.append(" and m.ApplyUser = :id");
		}
		if (StringUtils.isNotBlank(cellPhone)) {
			sb.append(" and m.CellPhone = :cellPhone");
		}
		if (StringUtils.isNotBlank(version)) {
			sb.append(" and d.Version = :version");
		}
		if (StringUtils.isNotBlank(organId)) {       //申請者機關, 社會局/資訊中心 全看
			if(organId.equals("397120000J") || organId.equals("397220100A")) {
				sb.append(" and d.ColumnId = 'reviewOrgan'");
			}else {
				sb.append(" and d.ColumnId = 'reviewOrgan' and  d.ColumnValue = :organName");
			}
		}
		if (StringUtils.isNotBlank(caseSet)) {       //好孕行得通服務
			sb.append(" and m.CaseSetId = :caseSet");
		}

		sb.append(" GROUP BY m.CaseId, m.ApplyDate, m.ApplyUserName, m.account,s.CaseSetName,m.DeadlineDate, m.Status,u2.Name, m.processId," +
				  " s.AcceptSet,s.CaseSetId,m.fDate,s.CaseFlowType,m.ApplyUserLoginType,d.Version" +
				  " Order By m.CaseId desc");

		Query query = entityManager.createNativeQuery(sb.toString());
		LOGGER.info("caseByOrganQuery sb="+sb);

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
		if (StringUtils.isNotBlank(caseFlowType)) {
			query.setParameter("caseFlowType", caseFlowType);
		}
		if (StringUtils.isNotBlank(id)) {
			query.setParameter("id", id);
		}
		if (StringUtils.isNotBlank(cellPhone)) {
			query.setParameter("cellPhone", cellPhone);
		}
		if (StringUtils.isNotBlank(version)) {
			query.setParameter("version", version);
		}
		if (StringUtils.isNotBlank(organId) && !organId.equals("397120000J") && !organId.equals("397220100A")) {
            KgoOrgan kgoOrgan =  kgoOrganRepository.findByOrganId(organId);
            if(kgoOrgan != null) {
            	query.setParameter("organName", kgoOrgan.getOrganName());
            }else {
            	query.setParameter("organName","");
            }
		}
		if (StringUtils.isNotBlank(caseSet)) {
			query.setParameter("caseSet", caseSet);
		}
		List listData = query.getResultList();
		List<CaseMainQueryCaseViewListDto> datas = Collections.synchronizedList(new ArrayList<>());
		if (listData != null && listData.size() > 0) {
			for (int i = 0; i < listData.size(); i++) {
				CaseMainQueryCaseViewListDto model = new CaseMainQueryCaseViewListDto();
				Object[] recordArray = (Object[]) listData.get(i);
				model.setCaseId((String)recordArray[0]);
				model.setApplyDate((Timestamp) recordArray[1]);
				model.setApplyUser((String)recordArray[2]);
				model.setCaseSetName((String)recordArray[3]);
				Date deadLine = (Date)recordArray[4];
				model.setDeadlineDate(new Timestamp(deadLine.getTime()) );
				//設定案件狀態描述
				CaseMainStatusEnum statusEnum = CaseMainStatusEnum.getCaseMainStatusEnum((String)recordArray[5]);
				model.setStatus(statusEnum == null ? "" : statusEnum.getLabel());
				model.setType((String)recordArray[6]);
				model.setCaseOfficer((String)recordArray[7]);
				model.setProcessId((String) recordArray[8]);
				model.setAcceptSet((String) recordArray[9]);
				model.setCaseSetId((String)recordArray[10]);
				model.setFdate((Timestamp) recordArray[11]);
				model.setCaseFlowType((String) recordArray[12]);
				model.setApplyUserLoginType((String) recordArray[13]);
				model.setVersion((Integer)recordArray[14]);
				datas.add(model);
			}
		}

		return datas;
	}


}
