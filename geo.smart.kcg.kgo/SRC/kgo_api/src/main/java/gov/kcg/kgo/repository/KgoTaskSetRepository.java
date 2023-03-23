package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoTaskSet;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoTaskSetRepositoryCustom;

public interface KgoTaskSetRepository extends BaseRepository<KgoTaskSet, Integer>, KgoTaskSetRepositoryCustom {

	/**
	 * 撈取已上架的所有活動資訊
	 * 
	 * @param isPublish
	 * @return
	 */
	public List<KgoTaskSet> findByIsPublishOrderByActivityDateDesc(boolean isPublish);
}
