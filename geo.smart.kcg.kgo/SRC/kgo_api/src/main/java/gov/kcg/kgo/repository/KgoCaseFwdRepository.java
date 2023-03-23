package gov.kcg.kgo.repository;

import gov.kcg.kgo.model.KgoCaseFwd;
import gov.kcg.kgo.model.KgoCaseFwdPK;
import gov.kcg.kgo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface KgoCaseFwdRepository
        extends BaseRepository<KgoCaseFwd, KgoCaseFwdPK> {

    /**
     * @param caseId
     * @return
     */
    List<KgoCaseFwd> findByIdCaseId(String caseId);

    /**
     * @param seq
     * @return
     */
    @Modifying
    int deleteByIdSeq(String seq);
}
