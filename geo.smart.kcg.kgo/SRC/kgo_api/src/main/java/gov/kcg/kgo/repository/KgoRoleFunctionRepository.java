package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import gov.kcg.kgo.dto.KgoSysMenuDto;
import gov.kcg.kgo.model.KgoRoleFunction;
import gov.kcg.kgo.model.KgoRoleFunctionPK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoRoleFunctionRepository extends BaseRepository<KgoRoleFunction, KgoRoleFunctionPK> {

	/**
	 * 取得角色權限 系統選單.
	 *
	 * @param roles the roles
	 * @return the kgo sys menu
	 */
	@Query(value= " select new gov.kcg.kgo.dto.KgoSysMenuDto(krf, kf) "
			+ " from "
			+ "		KgoFunction kf "
			+ "	left join "
			+ "		KgoRoleFunction krf "
			+ "	on "
			+ "		krf.id.functionId = kf.functionId "
			+ " where "
			+ "		kf.fSeq = 0 or krf.id.roleId in :roles or kf.functionId = 'serviceApplication' "
			+ "	order by "
			+ "		kf.orderNum ")
	public List<KgoSysMenuDto> getKgoSysMenu(List<String> roles);
}
