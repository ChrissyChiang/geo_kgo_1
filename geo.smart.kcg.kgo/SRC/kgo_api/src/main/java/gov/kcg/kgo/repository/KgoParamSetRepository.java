package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import gov.kcg.kgo.model.KgoParamSet;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoParamSetRepository extends BaseRepository<KgoParamSet, String> {

	/**
	 * 查出所有資料 order by Type.
	 *
	 * @return the list
	 */
	@Query(value = "Select s from KgoParamSet s Order By s.type")
	List<KgoParamSet> findAllOrderByType();
}
