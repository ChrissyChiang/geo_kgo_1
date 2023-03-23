package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.CasesetMydataQueryDto;
import gov.kcg.kgo.repository.custom.KgoCasesetMyDataRepositoryCustom;

public class KgoCasesetMyDataRepositoryCustomImpl implements KgoCasesetMyDataRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoGroupLevelRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 根據CasesetId查詢對應的MYDATA資料集合
	 * 
	 * @param caseSetId
	 * @return
	 */
	@Override
	public List<CasesetMydataQueryDto> countMydataIdAndNameByCaseSetId(String caseSetId) {

		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append(
				"select mr.MyDataId MYDATA_ID, mr.MyDataName MYDATA_NAME, mr.SType S_TYPE, count(cc.GroupSeq) COUNT ");
		sb.append("from KGO_CASESET_MYDATA cm  ");
		sb.append("left join KGO_MYDATA m on cm.myDataId = m.myDataId ");
		sb.append("left join KGO_CASESET_COLUMN cc on cm.CaseSetId = cc.CaseSetId and cm.MyDataId = cc.MyDataId  ");
		sb.append("left join KGO_MYDATA_RESOURCE mr on cm.MyDataId = mr.MyDataId ");
		sb.append("where cm.caseSetId = :caseSetId ");
		sb.append("group by mr.MyDataId, mr.MyDataName, mr.SType");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), CasesetMydataQueryDto.class);
		query.setParameter("caseSetId", caseSetId);

		return query.getResultList();
	}

}
