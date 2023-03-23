package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoMydataServiceResource;
import gov.kcg.kgo.model.KgoMydataServiceResourcePK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoMydataServiceResourceRepository
		extends BaseRepository<KgoMydataServiceResource, KgoMydataServiceResourcePK> {

	/**
	 * 
	 * @param myDataId
	 * @return
	 */
	public List<KgoMydataServiceResource> findByIdClientId(String clientId);

}
