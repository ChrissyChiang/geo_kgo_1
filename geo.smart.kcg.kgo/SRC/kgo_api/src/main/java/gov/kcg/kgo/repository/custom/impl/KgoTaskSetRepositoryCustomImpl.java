package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.EventNewsHomeDataGridDto;
import gov.kcg.kgo.dto.TaskAndAnnounceQueryDto;
import gov.kcg.kgo.dto.TaskSetQueryByActivitySeqDto;
import gov.kcg.kgo.dto.TaskSetQueryByCaseSetIdDto;
import gov.kcg.kgo.repository.custom.KgoTaskSetRepositoryCustom;

public class KgoTaskSetRepositoryCustomImpl implements KgoTaskSetRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoTaskSetRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 服務案件清單-案件維護-任務查詢
	 * 
	 * @param caseSetId //案件ID
	 * @return
	 */
	@Override
	public List<TaskSetQueryByCaseSetIdDto> findTaskByCaseSetId(String caseSetId) {

		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append("select s.TaskSeq TASK_SEQ,  s.ActivityName ACTIVITY_NAME, s.APP_KEY,s.ActivityDate,s.IsPublish  ");
		sb.append("from KGO_CASESET_TASK t Left join KGO_TASK_SET s on s.ActivitySeq=t.ActivitySeq ");
		sb.append("where t.CaseSetId= :caseSetId");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), TaskSetQueryByCaseSetIdDto.class);
		query.setParameter("caseSetId", caseSetId);

		return query.getResultList();
	}

	/**
	 * 任務及公告管理-任務以及公告查詢
	 * 
	 * @param name
	 * @param publishTimeStart
	 * @param publishTimeEnd
	 * @return
	 */
	@Override
	public List<TaskAndAnnounceQueryDto> findTaskAndAnnounceData(String name, String type, String publishTimeStart,
			String publishTimeEnd) {

		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append("Select SEQ, [TYPE], FUN_CODE, [NAME], PUBLISH_TIME, IS_PUBLISH,  UPDATE_TIME from ( ");
		sb.append(
				"Select Name [NAME], PublishTime PUBLISH_TIME, IsPublish IS_PUBLISH, ISNULL(ReleaseTo,'B') [TYPE], UpdateTime UPDATE_TIME, Seq SEQ,'AnnounceM' FUN_CODE from KGO_ANNOUNCE ");
		sb.append("Union ");
		sb.append(
				"Select ActivityName [NAME], PublishTime PUBLISH_TIME, IsPublish IS_PUBLISH,'F' [TYPE], UpdateTime UPDATE_TIME, ActivitySeq SEQ, 'TaskM' FUN_CODE from KGO_TASK_SET ");
		sb.append(") Ta  ");
		sb.append("Where 1=1  ");
		if (StringUtils.isNotBlank(name)) {
			sb.append("And [NAME] like CONCAT('%', :name,'%') ");
		}
		if (StringUtils.isNotBlank(type)) {
			sb.append("And [TYPE] = :type ");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			sb.append("And PUBLISH_TIME >= convert(datetime, :publishTimeStart) ");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			sb.append("And PUBLISH_TIME < convert(datetime, :publishTimeEnd)+1 ");
		}
		sb.append("Order by UPDATE_TIME desc");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), TaskAndAnnounceQueryDto.class);
		if (StringUtils.isNotBlank(name)) {
			query.setParameter("name", name);
		}
		if (StringUtils.isNotBlank(type)) {
			query.setParameter("type", type);
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			query.setParameter("publishTimeStart", publishTimeStart);
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			query.setParameter("publishTimeEnd", publishTimeEnd);
		}

		return query.getResultList();
	}

	/**
	 * 任務及公告管理-任務維護(新增/修改)-城市幣任務查詢
	 * 
	 * @param activitySeq
	 * @return
	 */
	@Override
	public List<TaskSetQueryByActivitySeqDto> findTaskByActivitySeq(int activitySeq) {

		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append(
				"Select s.ActivityName ACTIVITY_NAME, s.ContentHtml CONTENT_HTML, s.ActivityDate ACTIVITY_DATE, s.TaskSeq TASK_SEQ ");
		sb.append("from KGO_TASK_SET s where s.ActivitySeq=:activitySeq");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), TaskSetQueryByActivitySeqDto.class);
		query.setParameter("activitySeq", activitySeq);

		return query.getResultList();
	}

	@Override
	public List<EventNewsHomeDataGridDto> eventNewsfindTaskAndAnnounceData(boolean isPublish, String releaseTo) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append("Select SEQ, NAME, ACTIVITY_DATE, TYPE, FUN_CODE, IS_PUBLISH, PUBLISH_TIME ");
		sb.append("  from ( ");
		sb.append("    Select Name NAME, PublishTime PUBLISH_TIME, IsPublish IS_PUBLISH, ISNULL(ReleaseTo,'B') TYPE, ");
		sb.append("           '' ACTIVITY_DATE, Seq SEQ, 'AnnounceM' FUN_CODE, UpdateTime UPDATE_TIME ");
		sb.append("      from KGO_ANNOUNCE ");
		sb.append("     Union ");
		sb.append("    Select ActivityName NAME, PublishTime PUBLISH_TIME, IsPublish IS_PUBLISH, 'F' TYPE, ");
		sb.append("           ActivityDate ACTIVITY_DATE, ActivitySeq SEQ, 'TaskM' FUN_CODE, UpdateTime UPDATE_TIME ");
		sb.append("      from KGO_TASK_SET  ");
		sb.append("   ) Ta  ");
		sb.append(" Where 1=1  and TYPE =:releaseTo and IS_PUBLISH = :isPublish ORDER BY UPDATE_TIME DESC ");
		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), EventNewsHomeDataGridDto.class);
		query.setParameter("isPublish", isPublish);
		query.setParameter("releaseTo", releaseTo);

		return query.getResultList();
	}

}
