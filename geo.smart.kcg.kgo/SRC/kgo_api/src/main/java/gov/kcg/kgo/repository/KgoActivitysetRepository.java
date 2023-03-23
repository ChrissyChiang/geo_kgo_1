package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoActivityset;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoActivitysetRepositoryCustom;

public interface KgoActivitysetRepository
		extends BaseRepository<KgoActivityset, Integer>, KgoActivitysetRepositoryCustom {

}
