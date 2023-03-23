package gov.kcg.kgo.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.kcg.kgo.model.KgoUnit;
import gov.kcg.kgo.model.KgoUnitPK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoUnitRepository extends BaseRepository<KgoUnit, KgoUnitPK> {

	/**
	 * get unitName & unitId values by organId
	 * 
	 * @param organid
	 * @return
	 */
	public List<KgoUnit> findUnitIdAndUnitNameByIdOrganId(String organid);

	/**
	 * List all unit data of organId
	 * 
	 * @param organId
	 * @return
	 */
	public List<KgoUnit> findAllByIdOrganId(String organId);

	/**
	 * find kgounit by unitid
	 * 
	 * @param unitId
	 * @return
	 */
	public KgoUnit findByIdUnitId(String unitId);

	/**
	 * find kgounit by unitid
	 * 
	 * @param unitId
	 * @return
	 */
	public KgoUnit findByIdOrganIdAndIdUnitId(String organId, String unitId);

	/**
	 * delete kgounit by organId
	 * 
	 * @param organId
	 * @return
	 */
	public int deleteByIdOrganId(String organId);

	/**
	 * udpate kgounit new organId
	 * 
	 * @param organId
	 * @return
	 */
	@Modifying
	@Query(value = "Update KGO_UNIT SET OrganId =:newOrganId, UnitName =:unitName, UpdateTime=:updateTime, UpdateUser =:updateUser WHERE OrganId =:oldOrganId and UnitId =:unitId", nativeQuery = true)
	public int updateOrganId(String newOrganId, String oldOrganId, String unitId, String unitName, Timestamp updateTime,
			String updateUser);

}
