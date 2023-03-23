package gov.kcg.kgo.repository.custom.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.CaseManagerOrganQueryDto;
import gov.kcg.kgo.dto.OrganUnitManagementQueryDto;
import gov.kcg.kgo.model.KgoOrgan;
import gov.kcg.kgo.repository.custom.KgoOrganRepositoryCustom;

public class KgoOrganRepositoryCustomImpl implements KgoOrganRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoOrganRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	@Override
	public List<OrganUnitManagementQueryDto> findOrganAndUnitData(String organId, String organName, String unitId) {
		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append("  WITH Organ AS(");
		sb.append("  Select OrganId, OrganName, '' as UnitId, '' as UnitName ");
		sb.append("  From KGO_ORGAN");
		sb.append("  Where 1=1");
		if (StringUtils.isNotBlank(organName)) {
			sb.append("  And OrganName like CONCAT('%', :organName,'%')");
		}
		if (StringUtils.isNotBlank(organId)) {
			sb.append("  And OrganId = :organId");
		}
		sb.append(" ), OrganUnit AS(");
		sb.append("   Select o.OrganId, o.OrganName, u.UnitId as UnitId, u.UnitName as UnitName");
		sb.append("   From KGO_ORGAN o, KGO_UNIT u");
		sb.append("   Where o.OrganId = u.OrganId ");
		if (StringUtils.isNotBlank(organName)) {
			sb.append("   And OrganName like CONCAT('%', :organName,'%')");
		}
		if (StringUtils.isNotBlank(organId)) {
			sb.append("   And o.OrganId = :organId");
		}
		if (StringUtils.isNotBlank(unitId)) {
			sb.append("   And u.UnitId = :unitId");
		}
		sb.append(" )");
		sb.append("  Select OrganId, OrganName, UnitId as UnitId, UnitName as UnitName");
		sb.append("  From OrganUnit");
		sb.append("  union");
		sb.append("  Select Organ.OrganId, Organ.OrganName, Organ.UnitId as UnitId, Organ.UnitName as UnitName ");
		if (StringUtils.isNotBlank(unitId)) {
			sb.append("  From Organ, OrganUnit where Organ.OrganId = OrganUnit.OrganId");
		} else {
			sb.append(" 	From Organ");
		}
		sb.append("  ORDER BY OrganId asc, unitId asc");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), OrganUnitManagementQueryDto.class);
		if (ObjectUtils.isNotEmpty(organName)) {
			query.setParameter("organName", organName);
		}
		if (ObjectUtils.isNotEmpty(organId)) {
			query.setParameter("organId", organId);
		}
		if (StringUtils.isNotBlank(unitId)) {
			query.setParameter("unitId", unitId);
		}

		return query.getResultList();
	}

	@Override
	public List<KgoOrgan> findOrganByUserId(String userId) {
		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		// sb.append("WITH KgoUser as ( ");
		// sb.append("SELECT distinct(u.Organ) ");
		// sb.append("FROM KGO_USER_ROLE ur, KGO_USER u ");
		// sb.append("WHERE u.UserId = ur.UserId and ur.RoleId in ('");
		// sb.append(KgoRoleEnum.UNIT_M.getValue());
		// sb.append("', '");
		// sb.append(KgoRoleEnum.CASE_M.getValue());
		// sb.append("') and ur.UserId = :userId ");
		// sb.append(") ");
		// sb.append("SELECT o.* ");
		// sb.append("FROM KGO_ORGAN o, KgoUser ");
		// sb.append("WHERE o.OrganId = KgoUser.Organ");

		// 修改為單純撈取使用者所屬機關，不看ROLE
		sb.append("SELECT o.* FROM KGO_ORGAN o, KGO_USER u ")
				.append("WHERE (o.OrganId = u.Organ or o.ParentOrganId = u.Organ) ")
				.append("AND u.UserId= :userId");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), KgoOrgan.class);
		query.setParameter("userId", userId);

		return query.getResultList();
	}
	
	@Override
	public List<CaseManagerOrganQueryDto> findOrganByOrgan(String organId){
		StringBuilder sql = new StringBuilder();
		sql.append("select DISTINCT gl.Seq OrganSeq, gl.name OrganName ")
		   .append("  from KGO_ORGAN o ")
		   .append(" inner join KGO_GROUP_LEVEL gl on gl.OrganId = o.OrganId ");
		if (StringUtils.isNotBlank(organId)) {
			sql.append("where o.OrganId= :organId or ParentOrganId= :organId ");
		}

		Query query = entityManager.createNativeQuery(sql.toString(), CaseManagerOrganQueryDto.class);

		if (StringUtils.isNotBlank(organId)) {
			query.setParameter("organId", organId);
		}

		return query.getResultList();
	}
}
