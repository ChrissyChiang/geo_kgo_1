package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.model.KgoGroupLevel;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoGroupLevelRepositoryCustom;

public interface KgoGroupLevelRepository extends BaseRepository<KgoGroupLevel, Integer>, KgoGroupLevelRepositoryCustom {

	/**
	 * 
	 * @param mainType
	 * @return
	 */
	public List<KgoGroupLevel> findByMainTypeOrderByPublishTimeDesc(String mainType);

	/**
	 * 列出所有隸屬於某個主類別的次類別資料
	 * 
	 * @param mainType
	 * @return List<KgoGroupLevel>
	 */
	public List<KgoGroupLevel> findAllByMainType(String mainType);

    List<KgoGroupLevel> findAllByMainTypeAndState(String mainType, String state);

	/**
	 * 
	 * @param organId
	 * @return
	 */
	@Query(value = "select count(*) from KgoGroupLevel where organId =:organId")
	public Integer countByOrganId(@Param("organId") String organId);

	/**
	 * 
	 * @param organId
	 * @return
	 */
	@Query(value = "select count(*) from KgoGroupLevel where name =:name")
	public Integer countByName(@Param("name") String name);

	@Query("from KgoGroupLevel gl where gl.organId=:organId")
	KgoGroupLevel findByOrganId(@Param("organId")String organId);
}
