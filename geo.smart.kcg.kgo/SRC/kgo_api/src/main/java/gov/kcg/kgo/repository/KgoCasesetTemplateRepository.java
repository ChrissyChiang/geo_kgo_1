package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoCasesetTask;
import gov.kcg.kgo.model.KgoCasesetTaskPK;
import gov.kcg.kgo.model.KgoCasesetTemplate;
import gov.kcg.kgo.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface KgoCasesetTemplateRepository  extends BaseRepository<KgoCasesetTemplate, Integer> {


    List<KgoCasesetTemplate> findBySuspendAndOrgan(String suspend, String organ);

    List<KgoCasesetTemplate> findByNameLikeAndSuspendAndOrgan(String name, String suspend, String organ);

    Optional<KgoCasesetTemplate> findBySeqAndSuspend(Integer seq, String suspend);

    List<KgoCasesetTemplate> findByIsDefault(String isDefault);
}
