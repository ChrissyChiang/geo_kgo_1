package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import gov.kcg.kgo.model.KgoKeywordset;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoKeywordsetRepository extends BaseRepository<KgoKeywordset, Integer> {

	/**
	 * 
	 * @return
	 */
	@Query(value = "Select * from KGO_KEYWORDSET Order By OrderNum asc", nativeQuery = true)
	public List<KgoKeywordset> findOrderByOrderNumDesc();
}
