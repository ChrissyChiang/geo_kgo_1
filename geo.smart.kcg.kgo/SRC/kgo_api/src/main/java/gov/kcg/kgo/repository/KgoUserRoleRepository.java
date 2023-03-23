package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.model.KgoUserRole;
import gov.kcg.kgo.model.KgoUserRolePK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoUserRoleRepository extends BaseRepository<KgoUserRole, KgoUserRolePK> {

	/**
	 * Get all data by userId
	 * 
	 * @param userid
	 * @return
	 */
	public List<KgoUserRole> findAllByIdUserId(String userid);

	/**
	 * 取得使用者的角色字串(以逗號串接)
	 * 
	 * @param userId
	 * @return
	 */
	@Query(value = "select STRING_AGG(RoleId,',') from KGO_USER_ROLE where UserId = :userId", nativeQuery = true)
	public String getUserRoleStr(@Param("userId") String userId);

	/**
	 * Delete all data by userId
	 * 
	 * @param userid
	 * @return
	 */
	public List<KgoUserRole> deleteByIdUserId(String userid);

}
