package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.model.KgoUser;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoUserRepositoryCustom;

public interface KgoUserRepository extends BaseRepository<KgoUser, String>, KgoUserRepositoryCustom {

	KgoUser findByUserId(String userId);

	/**
	 * GEO 20211115 add 非市府員工登入後臺
	 * @param userValidate
	 * @param userLoginType
	 * @return
	 */
	public KgoUser findByUserValidateAndUserLoginType(String userValidate,String userLoginType);

	@Query(value = "select u " +
			"from KgoUserRole r " +
			"inner join KgoUser u on r.id.userId = u.userId " +
			"where r.id.roleId = :roleId and u.organ = :organ ")
	List<KgoUser> findByRoleIdAndOrgan(String roleId, String organ);

	@Query(value = "select u " +
			"from KgoUserRole r " +
			"inner join KgoUser u on r.id.userId = u.userId " +
			"where r.id.roleId = :roleId ")
	List<KgoUser> findByRoleId(String roleId);

	/**
	 * 根據機關來查詢資料
	 * 
	 * @param organId
	 * @return
	 */
	public List<KgoUser> findByOrgan(String organId);

//	/**
//	 * 取得機管管理者 Email
//	 * 
//	 * @param organId
//	 * @return
//	 */
//	@Query(value = "select STRING_AGG(u.Email,',') emails from KGO_USER_ROLE a inner join KGO_USER u on a.UserId = u.UserId where RoleId ='UNIT_M' and u.Organ =:organId and u.Email is not null and trim(u.Email) !=''", nativeQuery = true)
//	public String findEmailsByOrgan(@Param("organId") String organId);

	
	/**
	 * 取得機管管理者 Email
	 * 
	 * @param organId
	 * @return
	 */
	@Query(value = "select STRING_AGG(u.Email,',')from (select distinct u.Email  from KGO_CASESET_MANAGER m inner join KGO_USER u on u.UserId=m.Manager where  u.Email is not null and trim(u.Email) !='' and m.CaseSetId=:caseSetId ) u", nativeQuery = true)
	public String findEmailsByCaseSetId(@Param("caseSetId") String caseSetId);
	
	/**
	 * 查找服務案件 受理設定若為承辦 emails.
	 *
	 * @param caseSetId the case set id
	 * @return the string
	 */
	@Query(value = "select  STRING_AGG(u.Email, ',') from (select distinct u.Email from KGO_CASESET_OFFICER o left join KGO_USER u on u.UserId=o.CaseOfficer where  u.Email is not null and trim(u.Email) !='' and o.CaseSetId=:caseSetId ) u", nativeQuery = true)
	public String findOfficerEmailsByCaseSetId(@Param("caseSetId") String caseSetId);


	/**
	 * 查找服務案件 受理設定若為 機關分文 emails.
	 *
	 * @param caseSetId the case set id
	 * @return the string
	 */
	@Query(value = "select  STRING_AGG(u.Email, ',') from (select distinct u.Email from KGO_CASESET_UNIT un inner join KGO_USER u on u.Organ = un.Organ inner join KGO_USER_ROLE r on u.UserId=r.UserId and r.RoleId='UNIT_A' where  u.Email is not null and trim(u.Email) !='' and un.CaseSetId=:caseSetId ) u", nativeQuery = true)
	public String findUnitEmailsByCaseSetId(@Param("caseSetId") String caseSetId);
	
	/**
	 * 查找服務案件 受理設定若為 區機關  emails.
	 *
	 * @param caseSetId the case set id
	 * @return the string
	 */
	@Query(value = "select  STRING_AGG(u.Email, ',') from (select distinct u.Email from KGO_CASESET_AREA a inner join KGO_USER u on u.Organ = a.Organ inner join KGO_USER_ROLE r on u.UserId=r.UserId and r.RoleId='UNIT_A' where  u.Email is not null and trim(u.Email) !='' and a.CaseSetId=:caseSetId ) u", nativeQuery = true)
	public String findAreaEmailsByCaseSetId(@Param("caseSetId") String caseSetId);
	

}
