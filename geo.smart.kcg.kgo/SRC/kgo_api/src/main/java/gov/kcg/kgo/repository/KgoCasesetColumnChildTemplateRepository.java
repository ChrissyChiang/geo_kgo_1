package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoCasesetColumnChildTemplate;
import gov.kcg.kgo.model.KgoCasesetColumnTemplate;
import gov.kcg.kgo.model.KgoCasesetTemplate;
import gov.kcg.kgo.repository.base.BaseRepository;

import java.util.List;

public interface KgoCasesetColumnChildTemplateRepository extends BaseRepository<KgoCasesetColumnChildTemplate, Integer> {

    List<KgoCasesetColumnChildTemplate> findByColumnSeqInAndSuspend(List<Integer> columnSeq, String suspend);

}
