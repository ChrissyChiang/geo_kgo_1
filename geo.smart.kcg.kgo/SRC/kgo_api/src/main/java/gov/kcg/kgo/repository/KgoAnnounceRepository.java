package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoAnnounce;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoAnnounceRepository extends BaseRepository<KgoAnnounce, Integer> {

	/**
	 * get all by isPublish
	 * 
	 * @param organid
	 * @return
	 */
	public List<KgoAnnounce> findAllByIsPublishAndReleaseToOrderByPublishTimeDesc(boolean isPublish, String releaseTo);

}
