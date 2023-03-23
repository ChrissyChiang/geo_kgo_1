package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoCounty;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoCountyRepository extends BaseRepository<KgoCounty, String> {

	public List<KgoCounty> findAllByOrderBySortAsc();

}
