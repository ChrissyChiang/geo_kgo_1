package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoCasesetColumnTemplate;
import gov.kcg.kgo.model.KgoCasesetTemplate;
import gov.kcg.kgo.repository.base.BaseRepository;

import java.util.List;

public interface KgoCasesetColumnTemplateRepository extends BaseRepository<KgoCasesetColumnTemplate, Integer> {

    List<KgoCasesetColumnTemplate> findByTemplateSeqAndSuspend(Integer templateSeq, String suspend);

}
