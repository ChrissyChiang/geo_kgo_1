package gov.kcg.kgo.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.AccountManagementEditDto;
import gov.kcg.kgo.dto.AccountManagementQueryDto;
import gov.kcg.kgo.repository.custom.KgoUserRepositoryCustom;

public class KgoUserRepositoryCustomImpl implements KgoUserRepositoryCustom {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoGroupLevelRepositoryCustomImpl.class);

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 帳號權限管理-畫面初始
	 *
	 */
	@Override
	public List<AccountManagementQueryDto> findAccountQueryData() {
		return findAccountQueryData(null, null, null, null, null);
	}

	/**
	 * 帳號權限管理-帳號搜尋
	 * 
	 * @param organId 機關代碼
	 * @param unitId  單位代碼
	 * @param name    使用者姓名
	 * @param userId  使用者帳號
	 * @param roleId  角色代碼
	 */
	@Override
	public List<AccountManagementQueryDto> findAccountQueryData(String organId, String unitId, String name,
			String userId, String roleId) {

		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		/** GEO 20211115 add 非市府員工登入後台、GEO 20211125 add 跨機關檢視
		 * u.Email as EMAIL, u.PublicEmail as PUBLIC_EMAIL, u.UserLoginType as USER_LOGIN_TYPE, u.Tel as PHONE
		 * */
		sb.append("select o.OrganName as ORGAN_NAME, ut.UnitName as UNIT_NAME, u.UserId as USER_ID, u.Name as NAME, " +
				"u.Email as EMAIL, u.PublicEmail as PUBLIC_EMAIL, u.UserLoginType as USER_LOGIN_TYPE, u.Tel as PHONE, " +
				"u.IsAvailableCrossReview as IS_AVAILABLE_CROSS_REVIEW ");
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
		if (StringUtils.isNotBlank(userId)) {
			sb.append("and u.UserId = :userId ");
		}
		if (StringUtils.isNotBlank(roleId)) {
			sb.append("and r.RoleId = :roleId ");
		}
		sb.append("group by o.OrganName, ut.UnitName, u.UserId, u.Name, u.Email, u.PublicEmail, u.UserLoginType, u.Tel, u.IsAvailableCrossReview ");
		
		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), AccountManagementQueryDto.class);
		if (ObjectUtils.isNotEmpty(organId)) {
			query.setParameter("organId", organId);
		}
		if (ObjectUtils.isNotEmpty(unitId)) {
			query.setParameter("unitId", unitId);
		}
		if (StringUtils.isNotBlank(name)) {
			query.setParameter("name", name);
		}
		if (StringUtils.isNotBlank(userId)) {
			query.setParameter("userId", userId);
		}
		if (StringUtils.isNotBlank(roleId)) {
			query.setParameter("roleId", roleId);
		}

		return query.getResultList();
	}

	/**
	 * GEO 20211115 add 非市府員工登入後台
	 * 帳號權限管理-帳號搜尋
	 * @param userId
	 * @return
	 */
	@Override
	public List<AccountManagementQueryDto> findAccountQueryDataByUserId(String userId) {

		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append("select o.OrganName as ORGAN_NAME, ut.UnitName as UNIT_NAME, u.UserId as USER_ID, u.Name as NAME, " +
				"u.Email as EMAIL, u.PublicEmail as PUBLIC_EMAIL, u.UserLoginType as USER_LOGIN_TYPE, u.Tel as PHONE, " +
				"u.IsAvailableCrossReview as IS_AVAILABLE_CROSS_REVIEW ");
		sb.append("from KGO_USER u ");
		sb.append("left join KGO_USER_ROLE r on u.UserId = r.UserId ");
		sb.append("left join KGO_Organ o on u.Organ = o.OrganId ");
		sb.append("left join KGO_UNIT ut on u.Organ = ut.OrganId and u.Unit = ut.UnitId ");
		sb.append("where 1=1 ");
		if (StringUtils.isNotBlank(userId)) {
			sb.append("and u.UserId = :userId ");
		}
		sb.append("group by o.OrganName, ut.UnitName, u.UserId, u.Name, u.Email, u.PublicEmail, u.UserLoginType, u.Tel, u.IsAvailableCrossReview ");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), AccountManagementQueryDto.class);
		if (StringUtils.isNotBlank(userId)) {
			query.setParameter("userId", userId);
		}
		return query.getResultList();
	} //findAccountQueryDataByUserId

	/**
	 * 帳號權限管理-帳號維護(新增/修改)-資料搜尋
	 * 
	 * @param userId 使用者帳號
	 */
	@Override
	public List<AccountManagementEditDto> findAccountEditData(String userId) {

		StringBuilder sb = new StringBuilder();

		/**
		 * Generate custom query String
		 */
		sb.append(
				"Select o.OrganName as ORGAN_NAME, ut.UnitName as UNIT_NAME, u.UserId as USER_ID, u.Name as NAME, u.Email as EMAIL, u.PublicEmail as PUBLIC_EMAIL, u.Tel as TEL ");
		sb.append("from KGO_USER u ");
		sb.append("left join KGO_USER_ROLE r on u.Seq = r.UserId ");
		sb.append("left join KGO_Organ o on u.OrganId = o.OrganId ");
		sb.append("left join KGO_UNIT ut on u.OrganId = ut.OrganId and u.UnitId= ut.UnitId ");
		sb.append("where u. UserId = :userId ");

		/**
		 * Native Query
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), AccountManagementQueryDto.class);
		query.setParameter("userId", userId);

		return query.getResultList();
	}

}
