package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoKeywords;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoKeywordsRepositoryCustom;

public interface KgoKeywordsRepository extends BaseRepository<KgoKeywords, String>, KgoKeywordsRepositoryCustom {

}
