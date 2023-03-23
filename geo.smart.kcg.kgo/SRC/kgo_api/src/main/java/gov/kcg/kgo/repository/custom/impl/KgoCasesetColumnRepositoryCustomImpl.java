package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.CasesetMydataColumnDto;
import gov.kcg.kgo.dto.UraCaseViewExtraDataQueryDto;
import gov.kcg.kgo.repository.custom.KgoCasesetColumnRepositoryCustom;

public class KgoCasesetColumnRepositoryCustomImpl implements KgoCasesetColumnRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoCasesetColumnRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 查詢下一個 CaseId
	 * 
	 * @param caseIdPrefix
	 * @return
	 */
	@Override
	public List<UraCaseViewExtraDataQueryDto> getUraCaseExtraData(String caseId) {
		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append(
				"select cg.groupSeq GROUP_SEQ, cg.Memo MEMO, cg.[Version] VERSION, cc.ColumnName COLUMN_NAME, cc.ColumnType COLUMN_TYPE, cc.Length LENGTH, cc.ColumnId COLUMN_ID, cc.IsMustKey IS_MUST_KEY, cd.ColumnValue COLUMN_VALUE\r\n"
						+ "from KGO_CASESET_COLUMN cc \r\n" + "right join KGO_CASESET_GROUP cg on cg.CaseSetId = cc.CaseSetId and cg.GroupSeq = cc.GroupSeq\r\n"
						+ "left join KGO_CASE_DETAIL cd on cc.ColumnId = cd.ColumnId and cc.Version = cd.Version \r\n" + "right join KGO_URA_APPLY ua on cc.CaseSetId = ua.CaseSetId\r\n"
						+ "where ua.caseId = :caseId and cg.version = (\r\n"
						+ "	select max(cg.version) as maxVersion from KGO_URA_APPLY ua left join KGO_CASESET_GROUP cg on ua.CaseSetId = cg.CaseSetId where ua.caseId = :caseId\r\n" + ")\r\n"
						+ "order by cg.GroupSeq");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), UraCaseViewExtraDataQueryDto.class);
		query.setParameter("caseId", caseId);
		return query.getResultList();
	}

	@Override
	public List<CasesetMydataColumnDto> findMydataColumnByCaseSetAndVersion(String caseSetId, int version) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append("select a.CaseSetId,a.Version,a.ColumnId,a.MyDataId,a.MyDataColumn,b.MyDataClientId, c.MyDataType, c.FileName,c.HeaderRow,c.DataStartRow ,c.Type " + "  from KGO_CASESET_COLUMN a "
				+ "  left join KGO_CASESET_MYDATA b on a.CaseSetId = b.CaseSetId and a.MyDataId = b.MyDataId left join KGO_MYDATA_COLUMN c "
				+ "    on a.MyDataId = c.MyDataId and a.MyDataColumn = c.MyDataColumn where a.CaseSetId = :caseSetId and a.Version = :version ");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), CasesetMydataColumnDto.class);
		query.setParameter("caseSetId", caseSetId);
		query.setParameter("version", version);
		return query.getResultList();
	}

}
