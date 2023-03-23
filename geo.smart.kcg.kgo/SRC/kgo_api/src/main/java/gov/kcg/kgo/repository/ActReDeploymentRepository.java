package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.ActReDeployment;
import gov.kcg.kgo.model.KgoAnnounce;
import gov.kcg.kgo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActReDeploymentRepository extends BaseRepository<ActReDeployment, String> {

    @Query(nativeQuery = true, value = "SELECT Top 1 * FROM ACT_RE_DEPLOYMENT C ORDER BY C.DEPLOY_TIME_ DESC")
    ActReDeployment findFirstOrderByDeployTimeDesc();
}
