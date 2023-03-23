package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoOrgan;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoOrganRepositoryCustom;

public interface KgoOrganRepository extends BaseRepository<KgoOrgan, String>, KgoOrganRepositoryCustom {

	/**
	 * 查詢機關資料By organId
	 * 
	 * @param organId
	 * @param organName
	 * @return
	 */
	public KgoOrgan findByOrganId(String organId);
	
	/**
	 * 查詢機關資料By organId or organName
	 * 
	 * @param organId
	 * @param organName
	 * @return
	 */
	public KgoOrgan findByOrganNameOrOrganId(String organId, String organName);

	/**
	 * 
	 * @param organId
	 * @param parentOrganId
	 * @return
	 */
	public List<KgoOrgan> findByOrganIdOrParentOrganId(String organId, String parentOrganId);
}
