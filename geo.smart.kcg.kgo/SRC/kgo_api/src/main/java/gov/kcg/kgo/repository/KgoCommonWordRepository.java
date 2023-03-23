package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoCommonWord;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoCommonWordRepositoryCustom;

public interface KgoCommonWordRepository extends BaseRepository<KgoCommonWord, Integer>, KgoCommonWordRepositoryCustom {

}
